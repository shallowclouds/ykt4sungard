/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//httpclient/src/java/org/apache/commons/httpclient/ChunkedInputStream.java,v 1.24 2004/10/10 15:18:55 olegk Exp $
 * $Revision: 291181 $
 * $Date: 2005-09-23 14:13:25 -0400 (Fri, 23 Sep 2005) $
 *
 * ====================================================================
 *
 *  Copyright 2002-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.commons.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.httpclient.util.ExceptionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>Transparently coalesces chunks of a HTTP stream that uses
 * Transfer-Encoding chunked.</p>
 *
 * <p>Note that this class NEVER closes the underlying stream, even when close
 * gets called.  Instead, it will read until the "end" of its chunking on close,
 * which allows for the seamless invocation of subsequent HTTP 1.1 calls, while
 * not requiring the client to remember to read the entire contents of the
 * response.</p>
 *
 * @author Ortwin Glueck
 * @author Sean C. Sullivan
 * @author Martin Elwin
 * @author Eric Johnson
 * @author <a href="mailto:mbowler@GargoyleSoftware.com">Mike Bowler</a>
 * @author Michael Becke
 * @author <a href="mailto:oleg@ural.ru">Oleg Kalnichevski</a>
 *
 * @since 2.0
 *
 */
public class ChunkedInputStream extends InputStream {
    /** The inputstream that we're wrapping */
    private InputStream in;

    /** The chunk size */
    private int chunkSize;

    /** The current position within the current chunk */
    private int pos;

    /** True if we'are at the beginning of stream */
    private boolean bof = true;

    /** True if we've reached the end of stream */
    private boolean eof = false;

    /** True if this stream is closed */
    private boolean closed = false;

    /** The method that this stream came from */
    private HttpMethod method = null;

    /** Log object for this class. */
    private static final Log LOG = LogFactory.getLog(ChunkedInputStream.class);

    /**
     * ChunkedInputStream constructor that associates the chunked input stream with a 
     * {@link HttpMethod HTTP method}. Usually it should be the same {@link HttpMethod 
     * HTTP method} the chunked input stream originates from. If chunked input stream 
     * contains any footers (trailing headers), they will be added to the associated 
     * {@link HttpMethod HTTP method}.
     *
     * @param in the raw input stream
     * @param method the HTTP method to associate this input stream with. Can be <tt>null</tt>.  
     *
     * @throws IOException If an IO error occurs
     */
    public ChunkedInputStream(
        final InputStream in, final HttpMethod method) throws IOException {
            
        if (in == null) {
            throw new IllegalArgumentException("InputStream parameter may not be null");
        }
        this.in = in;
        this.method = method;
        this.pos = 0;
    }

    /**
     * ChunkedInputStream constructor
     *
     * @param in the raw input stream
     *
     * @throws IOException If an IO error occurs
     */
    public ChunkedInputStream(final InputStream in) throws IOException {
        this(in, null);
    }
    
    /**
     * <p> Returns all the data in a chunked stream in coalesced form. A chunk
     * is followed by a CRLF. The method returns -1 as soon as a chunksize of 0
     * is detected.</p>
     * 
     * <p> Trailer headers are read automcatically at the end of the stream and
     * can be obtained with the getResponseFooters() method.</p>
     *
     * @return -1 of the end of the stream has been reached or the next data
     * byte
     * @throws IOException If an IO problem occurs
     * 
     * @see HttpMethod#getResponseFooters()
     */
    public int read() throws IOException {

        if (closed) {
            throw new IOException("Attempted read from closed stream.");
        }
        if (eof) {
            return -1;
        } 
        if (pos >= chunkSize) {
            nextChunk();
            if (eof) { 
                return -1;
            }
        }
        pos++;
        return in.read();
    }

    /**
     * Read some bytes from the stream.
     * @param b The byte array that will hold the contents from the stream.
     * @param off The offset into the byte array at which bytes will start to be
     * placed.
     * @param len the maximum number of bytes that can be returned.
     * @return The number of bytes returned or -1 if the end of stream has been
     * reached.
     * @see java.io.InputStream#read(byte[], int, int)
     * @throws IOException if an IO problem occurs.
     */
    public int read (byte[] b, int off, int len) throws IOException {

        if (closed) {
            throw new IOException("Attempted read from closed stream.");
        }

        if (eof) { 
            return -1;
        }
        if (pos >= chunkSize) {
            nextChunk();
            if (eof) { 
                return -1;
            }
        }
        len = Math.min(len, chunkSize - pos);
        int count = in.read(b, off, len);
        pos += count;
        return count;
    }

    /**
     * Read some bytes from the stream.
     * @param b The byte array that will hold the contents from the stream.
     * @return The number of bytes returned or -1 if the end of stream has been
     * reached.
     * @see java.io.InputStream#read(byte[])
     * @throws IOException if an IO problem occurs.
     */
    public int read (byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    /**
     * Read the CRLF terminator.
     * @throws IOException If an IO error occurs.
     */
    private void readCRLF() throws IOException {
        int cr = in.read();
        int lf = in.read();
        if ((cr != '\r') || (lf != '\n')) { 
            throw new IOException(
                "CRLF expected at end of chunk: " + cr + "/" + lf);
        }
    }


    /**
     * Read the next chunk.
     * @throws IOException If an IO error occurs.
     */
    private void nextChunk() throws IOException {
        if (!bof) {
            readCRLF();
        }
        chunkSize = getChunkSizeFromInputStream(in);
        bof = false;
        pos = 0;
        if (chunkSize == 0) {
            eof = true;
            parseTrailerHeaders();
        }
    }

    /**
     * Expects the stream to start with a chunksize in hex with optional
     * comments after a semicolon. The line must end with a CRLF: "a3; some
     * comment\r\n" Positions the stream at the start of the next line.
     *
     * @param in The new input stream.
     * @param required <tt>true<tt/> if a valid chunk must be present,
     *                 <tt>false<tt/> otherwise.
     * 
     * @return the chunk size as integer
     * 
     * @throws IOException when the chunk size could not be parsed
     */
    private static int getChunkSizeFromInputStream(final InputStream in) 
      throws IOException {
            
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // States: 0=normal, 1=\r was scanned, 2=inside quoted string, -1=end
        int state = 0; 
        while (state != -1) {
        int b = in.read();
            if (b == -1) { 
                throw new IOException("chunked stream ended unexpectedly");
            }
            switch (state) {
                case 0: 
                    switch (b) {
                        case '\r':
                            state = 1;
                            break;
                        case '\"':
                            state = 2;
                            /* fall through */
                        default:
                            baos.write(b);
                    }
                    break;

                case 1:
                    if (b == '\n') {
                        state = -1;
                    } else {
                        // this was not CRLF
                        throw new IOException("Protocol violation: Unexpected"
                            + " single newline character in chunk size");
                    }
                    break;

                case 2:
                    switch (b) {
                        case '\\':
                            b = in.read();
                            baos.write(b);
                            break;
                        case '\"':
                            state = 0;
                            /* fall through */
                        default:
                            baos.write(b);
                    }
                    break;
                default: throw new RuntimeException("assertion failed");
            }
        }

        //parse data
        String dataString = EncodingUtil.getAsciiString(baos.toByteArray());
        int separator = dataString.indexOf(';');
        dataString = (separator > 0)
            ? dataString.substring(0, separator).trim()
            : dataString.trim();

        int result;
        try {
            result = Integer.parseInt(dataString.trim(), 16);
        } catch (NumberFormatException e) {
            throw new IOException ("Bad chunk size: " + dataString);
        }
        return result;
    }

    /**
     * Reads and stores the Trailer headers.
     * @throws IOException If an IO problem occurs
     */
    private void parseTrailerHeaders() throws IOException {
        Header[] footers = null;
        try {
            String charset = "US-ASCII";
            if (this.method != null) {
                charset = this.method.getParams().getHttpElementCharset();
            }
            footers = HttpParser.parseHeaders(in, charset);
        } catch(HttpException e) {
            LOG.error("Error parsing trailer headers", e);
            IOException ioe = new IOException(e.getMessage());
            ExceptionUtil.initCause(ioe, e); 
            throw ioe;
        }
        if (this.method != null) {
            for (int i = 0; i < footers.length; i++) {
                this.method.addResponseFooter(footers[i]);
            }
        }
    }

    /**
     * Upon close, this reads the remainder of the chunked message,
     * leaving the underlying socket at a position to start reading the
     * next response without scanning.
     * @throws IOException If an IO problem occurs.
     */
    public void close() throws IOException {
        if (!closed) {
            try {
                if (!eof) {
                    exhaustInputStream(this);
                }
            } finally {
                eof = true;
                closed = true;
            }
        }
    }

    /**
     * Exhaust an input stream, reading until EOF has been encountered.
     *
     * <p>Note that this function is intended as a non-public utility.
     * This is a little weird, but it seemed silly to make a utility
     * class for this one function, so instead it is just static and
     * shared that way.</p>
     *
     * @param inStream The {@link InputStream} to exhaust.
     * @throws IOException If an IO problem occurs
     */
    static void exhaustInputStream(InputStream inStream) throws IOException {
        // read and discard the remainder of the message
        byte buffer[] = new byte[1024];
        while (inStream.read(buffer) >= 0) {
            ;
        }
    }
}
