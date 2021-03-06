/*************************************************************************
*	System		: POPLAR/ANZU_L
*	File Name	: LST_DSCR.C
*	Author		: 桑原 美紀
*	Date		: 1997/03/11
*	RealTimeOS	: ＲＩＳＣ（ＳＨ７０４３）モニター
*	Description	: 親展受信消去通知
*	Maintenance	:
*
*	Copyright (C) 1996 Murata Machinery,Ltd. All rights reserved.
*************************************************************************/
#include	"\src\atlanta\define\product.h"
#include	"\src\atlanta\define\apl_msg.h"
#include	"\src\atlanta\define\std.h"
#include	"\src\atlanta\define\sysmailb.h"

#include	"\src\atlanta\define\cmn_def.h"
#include	"\src\atlanta\define\lst_def.h"
#include	"\src\atlanta\define\mem_def.h"

#include	"\src\atlanta\define\cmn_pro.h"
#include	"\src\atlanta\define\lst_pro.h"
#include	"\src\atlanta\define\mem_pro.h"

#include	"\src\atlanta\ext_v\bkupram.h"
#include	"\src\atlanta\ext_v\lst_data.h"
#include	"\src\atlanta\ext_v\lst_wrd.h"
#include	"\src\atlanta\ext_v\sys_data.h"
#if ( PRO_PANEL == ANK )
#include "\src\atlanta\opr\ank\define\opr_def.h"
#endif
#if ( PRO_PANEL == KANJI )
#include "\src\atlanta\opr\kanji\define\opr_def.h"
#endif

/*************************************************************************
	module		:[親展受信消去通知印刷]
	function	:[親展受信消去通知を印刷する]
	common		:[
		List		:
		SYB_MailBox	:
		SYS_DocumentPrintNo		:
	]
	condition	:[
		印刷モード
		'	LST_DELSECURE_NOTICE	親展受信消去通知
		'	LST_POWEROFF_NOTICE		停電検出通知
	]
	commment	:[
		94/11/26	By j.masuda
			親展受信消去通知のステータスクリアーをメモリがない時のみ
			SYS_MAIL_SETとする。またメモリのクリアーはメインで行う事とし
			リストではおこなわない。（停電時のみ）
		94/11/29	By j.masuda
			親展受信消去通知はステータスがSYS_MAILBOX_RECEIVEで、かつ、
			メモリがない時のみ印刷する。

		1997/10/18 Y.Matsukuma 
			親展メモリ期間が過ぎたときの消去通知がおかしくなるので
			Print_DelSecure_Notice_Specifi((UBYTE)SYS_DocumentPrintNo)
			にモード（Mode）を追加してコンペアさせました （ポプラ、アンズ共通）
	]
	return		:[印刷結果]
	machine		:[SH043]
	language	:[SHC]
	keyword		:[LST]
	date		:[93/11/25]
	author		:[増田次郎]
*************************************************************************/
WORD				Print_DelSecure_Notice(
						 UBYTE Mode )	/*印刷モード*/
{
	UBYTE	i;						/*loop*/
	UBYTE	mail_cnt;					/*ﾃﾞｰﾀｶｳﾝﾄ*/
	UBYTE	page;						/*ﾍﾟｰｼﾞ*/
	UBYTE	line_cnt;					/*行数*/
	register WORD	ret;					/*実行結果*/

	/*消去ﾃﾞｰﾀ存在検査*/
	if ( Mode == LST_DELSECURE_NOTICE ) {
		if ( SYB_MailBox[SYS_DocumentPrintNo].Status != SYS_MAILBOX_RECEIVE ) {
			return ( MSG_PRN_LST_NO_LIST );
		}
	}
	else {
		if ( Check_AnyList( LST_DELSECURE_NOTICE ) == MSG_PRN_LST_NO_LIST ) {
			return ( MSG_PRN_LST_NO_LIST );
		}
	}

	mail_cnt	= 0;
	page		= 1;
	while( mail_cnt != SYS_MAILBOX_MAX ){
		if ( (ret = CharacterPrint( LST_OPEN )) != OK ) {
			return ( ret );
		}

		if ( page == 1 ) {
			line_cnt = (UBYTE)(PaperSizeLen() - 15);/*印刷可能行数ｾｯﾄ*/
		}
		else {
			line_cnt = (UBYTE)(PaperSizeLen() - 6);/*印刷可能行数ｾｯﾄ*/
		}
		if ( (ret = Print_DelSecure_Notice_Head( 0, page ) ) != OK ) {/*ﾍｯﾀﾞ印刷*/
			return ( ret );
		}
		if ( Mode == LST_DELSECURE_NOTICE ) {
			if ( SYB_MailBox[SYS_DocumentPrintNo].Status == SYS_MAILBOX_RECEIVE ) {
				if ( (ret = Print_DelSecure_Notice_Specifi((UBYTE)SYS_DocumentPrintNo, Mode)) != OK ) {
					return ( ret );
				}
				if ( (ret = Print_DelSecure_Notice_Head( 1, page ) ) != OK ) {/*ﾍｯﾀﾞ印刷*//* 追加1997/10/18 Y.Matsukuma */
					return ( ret );
				}
				SYB_MailBox[SYS_DocumentPrintNo].Status = SYS_MAILBOX_SET;
				MEM_ClearDocAndFATArea( (UBYTE)SYS_MEM_SECURE_RX_FILE, SYS_DocumentPrintNo, MEM_IGNORE_BOX_NUMBER);
				break;
			}
		}
		else {
			for ( i = 0; line_cnt >= 2; i++ ) {
				while ( mail_cnt < SYS_MAILBOX_MAX ) {
					if (( SYB_MailBox[mail_cnt].Status == SYS_MAILBOX_RECEIVE ) &&
						( MEM_CountTotalPage(SYS_MEM_SECURE_RX_FILE,mail_cnt, MEM_IGNORE_BOX_NUMBER)
																 == MEM_NO_INDEX )) {
						break;
					}
					mail_cnt++;
				}
				if( mail_cnt >= SYS_MAILBOX_MAX ){
					break;
				}
				/*�ｾ�｢�｢�｢�｢�｢�ﾞ�｢�｢�ﾞ�｢�｢�ﾞ�｢�｢�ﾞ�｢�｢�ﾆ*/
				if(( page == 1 ) ||					/* １ページ目の時 */
				   (( page != 1 ) && ( i != 0 ))){	/* 1ページ,１行目以外の時 */
					Print_Secure_Notice_List( 2, List.S.Print, List.S.Attribute	 );
					if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
						return ( ret );
					}
				}
				if ( (ret = Print_DelSecure_Notice_Specifi( mail_cnt, Mode )) != OK ) {
					return ( ret );
				}
				SYB_MailBox[mail_cnt].MessageStatus = SYS_MAILBOX_MSG_EMPTY;
				SYB_MailBox[mail_cnt].Status = SYS_MAILBOX_SET;
				line_cnt	-= 2;
				mail_cnt++;
			}
		}
		if ( (ret = Print_DelSecure_Notice_Head( 1, page ) ) != OK ) {/*ﾍｯﾀﾞ印刷*/
			return ( ret );
		}
		while ( mail_cnt < SYS_MAILBOX_MAX ) {
			if (( SYB_MailBox[mail_cnt].Status == SYS_MAILBOX_RECEIVE ) &&
				( MEM_CountTotalPage(SYS_MEM_SECURE_RX_FILE,mail_cnt, MEM_IGNORE_BOX_NUMBER) == MEM_NO_INDEX )) {
				break;
			}
			mail_cnt++;
		}
		if( mail_cnt >= SYS_MAILBOX_MAX ){
			break;
		}
		if ( (ret = CharacterPrint( LST_CLOSE )) != OK ) {
			return ( ret );
		}
		page++;
	}

	/* ｼﾝﾃﾝｼﾞｭｼﾝ ｹﾞﾝｺｳｶﾞ ｼｮｳｷｮ ｻﾚﾏｼﾀ ..*/
	Prn_Att_Str_Clear( List.S.Print, List.S.Attribute );
	AttributeSet( &List.S.Print[21-1], &List.S.Attribute[21-1],
	  PWRD_ThisSecMailEra, LST_NOT );
	if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
		return ( ret );
	}

	/*１行印字（改行）*/
	if ( (ret = CharacterPrint( LST_LINE_FEED )) != OK ) {
		return ( ret );
	}

	if ( (ret = CharacterPrint( LST_CLOSE )) != OK ) {
		return ( ret );
	}
	return ( OK );
}

/*************************************************************************
	module		:[親展受信消去通知ヘッダ印刷]
	functiom	:[親展受信消去通知のヘッダを印刷する]
	common		:[
		List	:
	]
	condition	:[
		ﾍｯﾀﾞ種類
			0->ﾍﾟｰｼﾞ先頭見出し
			1->ﾍﾟｰｼﾞｴﾝﾄﾞ見出し
	]
	commment	:[]
	return		:[印刷結果]
	machine		:[SH7043]
	language	:[SHC]
	keyword		:[LST]
	date		:[97/10/13]
	author		:[桑原美紀]
*************************************************************************/
WORD				Print_DelSecure_Notice_Head(
						 UBYTE Category,	/*ヘッダ種類*/
						 UBYTE Page )		/*ページ*/
{
	register WORD	ret;					/*実行結果*/

	if ( Category == 0 ) {
		if ( Page == 1 ) {
			/*１改行＋発信元名、番号印刷＋２改行*/
			if ( (ret = TTI_ID_Print_Feed()) != OK ) {
				return ( ret );
			}

			/*�ｮ�｢�｢�｢�｢�｢�｢�｢�｢�｢�｢�｢�｢�｢�ｲ*/
			Prn_Att_Str_Clear( List.S.Print, List.S.Attribute );
			List.S.Print[20-1] = LST_KLINE7;
			CMN_MemorySet( &List.S.Print[21-1], 40, LST_KLINE_ );
			List.S.Print[61-1] = LST_KLINE9;
			if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
				return ( ret );
			}

			/*�､シンテンジュシン　ショウキョ　ツウチ�､*/
			Prn_Att_Str_Clear( List.S.Print, List.S.Attribute );
			List.S.Print[20-1] = LST_KLINEI;
#if ( PRO_PANEL == KANJI ) || ( PRO_JIS_CODE == ENABLE )
			AttributeSet( &List.S.Print[23-1], &List.S.Attribute[23-1],
			 PWRD_ErasedDocuments_K, LST_NOT );			/* MES→INV 94.7.20 ->NOT 8.10 */
#else
			AttributeSet( &List.S.Print[23-1], &List.S.Attribute[23-1],
			  PWRD_ErasedDocuments, LST_MES_TWI_XXX );
#endif
			List.S.Print[61-1] = LST_KLINEI;
			if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
				return ( ret );
			}

			/*�ｶ�｢�｢�｢�｢�｢�｢�｢�｢�｢�｢�｢�｢�ｺ*/
			Prn_Att_Str_Clear( List.S.Print, List.S.Attribute );
			List.S.Print[20-1] = LST_KLINE1;
			CMN_MemorySet( &List.S.Print[21-1], 40, LST_KLINE_ );
			List.S.Print[61-1] = LST_KLINE3;
			if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
				return ( ret );
			}

			/*１行印字（改行）*/
			if ( (ret = CharacterPrint( LST_LINE_FEED )) != OK ) {
				return ( ret );
			}

			/*現在時間*/
			if ( (ret = CurrentTimePrint( 41-1 )) != OK ) {
				return ( ret );
			}

			/*�ｮ�｢�｢�｢�｢�｢�｢�ﾎ�｢�｢�｢�｢�｢�｢�ｲ*/
			Print_Secure_Notice_List( 0, List.S.Print, List.S.Attribute	 );
			if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
				return ( ret );
			}

			/*�､No�､ｼﾝﾃﾝｼｬﾒｲ�､ ｱｲﾃｻｷﾒｲ　　�､*/
			Print_Secure_Notice_List( 1, List.S.Print, List.S.Attribute	 );
			if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
				return ( ret );
			}

		}
		else{
			/*２行印字（改行）*/
			if ( (ret = CharacterPrint( LST_LINE_FEED +2 )) != OK ) {
				return ( ret );
			}

			/*�ｮ�｢�｢�｢�｢�｢�｢�ﾎ�｢�｢�｢�｢�｢�｢�ｲ*/
			Print_Secure_Notice_List( 0, List.S.Print, List.S.Attribute	 );
			if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
				return ( ret );
			}
		}
	}
	else{
		/*�ｶ�｢�｢�｢�｢�｢�｢�ﾖ�｢�｢�｢�｢�｢�｢�ｺ*/
		Print_Secure_Notice_List( 4, List.S.Print, List.S.Attribute	 );
		if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
			return ( ret );
		}

		/*１行印字（改行）*/
		if ( (ret = CharacterPrint( LST_LINE_FEED )) != OK ) {
			return ( ret );
		}
	}
}

/*************************************************************************
	module		:[親展受信消去通知の明細部印刷]
	function	:[親展受信消去通知の明細部分を印刷する]
	common		:[
		List		:
		SYB_MailBox	:
	]
	condition	:[]
	commment	:[]
	return		:[印刷結果]
	machine		:[SH7043]
	language	:[SHC]
	keyword		:[LST]
	date		:[93/11/25]
	author		:[増田次郎]
*************************************************************************/
WORD				Print_DelSecure_Notice_Specifi(UBYTE MailNumber , /*親展受信データ番号*/
												   UBYTE mode)		  /*印刷モード*/
{
	register WORD	ret;					/*実行結果*/

	if (mode == LST_DELSECURE_NOTICE) {	/* 追加	1997/10/18 Y.Matsukuma */
		/*�ｾ�｢�ﾞ�｢�｢�｢�｢�ﾞ�｢�｢�｢�｢�｢�｢�ﾆ*/
		Print_Secure_Notice_List( 2, List.S.Print, List.S.Attribute	 );
		if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
			return ( ret );
		}
	}

	/*�､9�､xxxxxxxx�､xxxxxxxxxxxx�､*/
	Print_Secure_Notice_List( 3, List.S.Print, List.S.Attribute	 );
	/* No. */
#if ( PRO_CPU == SH7043 )	/* FX,MMLで親展BOXNo.が、0〜or1〜と違う時のため */
	CMN_UnsignedIntToASC( &List.S.Print[21-1], (UWORD)MailNumber + ( OPR_MAILBOX_MIN ), 2, ' ' );
#else
	CMN_UnsignedIntToASC( &List.S.Print[22-1], (UWORD)MailNumber, 1, '0' );
#endif
	/*ｼﾝﾃﾝｼｬﾒｲ*/
	CMN_StringCopy( &List.S.Print[24-1], &SYB_MailBox[MailNumber].BoxName[0] );
	/*ｱｲﾃｻｷﾒｲ*/
	CMN_StringCopy( &List.S.Print[41-1], &SYB_MailBox[MailNumber].RemoteName[0]);
	if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
		return ( ret );
	}
	return ( OK );
}

