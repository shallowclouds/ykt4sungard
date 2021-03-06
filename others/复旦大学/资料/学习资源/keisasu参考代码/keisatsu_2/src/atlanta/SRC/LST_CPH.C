/*************************************************************************
*	System		: POPLAR/ANZU_L
*	File Name	: LST_CPH.C
*	Author		: �K�� ���I
*	Date		: 1997/06/12
*	RealTimeOS	: �q�h�r�b�i�r�g�V�O�S�R�j���j�^�[
*	Description	: �Í�����M�ʒm
*	Maintenance	:
*
*	Copyright (C) 1996 Murata Machinery,Ltd. All rights reserved.
*************************************************************************/
#include	"\src\atlanta\define\product.h"
#include	"\src\atlanta\define\apl_msg.h"
#include	"\src\atlanta\define\std.h"
#include	"\src\atlanta\define\sysmax.h"

#include	"\src\atlanta\define\cmn_def.h"
#include	"\src\atlanta\define\lst_def.h"

#include	"\src\atlanta\define\cmn_pro.h"
#include	"\src\atlanta\define\lst_pro.h"

#include	"\src\atlanta\ext_v\bkupram.h"
#include	"\src\atlanta\ext_v\lst_data.h"
#include	"\src\atlanta\ext_v\lst_wrd.h"

#if (PRO_CIPHER == ENABLE)
/*************************************************************************
	module		:[�Í�����M�ʒm���]
	function	:[�Í�����M�ʒm���������]
	common		:[
		List			:
		SYB_CipherBox	:
	]
	condition	:[
	]
	commment	:[
	]
	return		:[�������]
	machine		:[SH7043]
	language	:[SHC]
	keyword		:[LST]
	date		:[96/06/12]
	author		:[�K�����I]
*************************************************************************/
WORD		Print_CipherRecieve_Notice( void )
{
	UBYTE	cipher_no;				/* ��M�m�� */
	register WORD	ret;			/*���s����*/

#if (0)			/* �����ύX 97/12/12 By M.Kuwahara */
	/* �P�s���s�{���M�����C���M���ԍ��{�Q�s���s����{���o���{�P�s���s */
// #if ( PRO_PANEL == KANJI ) || ( PRO_JIS_CODE == ENABLE )
//	if ( (ret = Print_Head1_Common( PWRD_CipherReceiveNotice_K,
//	 LST_NOT,20-1 )) != OK ) {
//		return ( ret );
//	}
//
// #else
//	if ( (ret = Print_Head1_Common( PWRD_CipherReceiveNotice,
//	  LST_XXX_TWI_XXX, 14 )) != OK ) {
//		return ( ret );
//	}
//
// #endif
#endif

	/*������������������������������*/
	Prn_Att_Str_Clear( List.S.Print, List.S.Attribute );
	List.S.Print[19-1] = LST_KLINE7;
	CMN_MemorySet( &List.S.Print[20-1], 41, LST_KLINE_ );
	List.S.Print[61-1] = LST_KLINE9;
	if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
		return ( ret );
	}

	/* |�Í�����M�ʒm| */
	Prn_Att_Str_Clear( List.S.Print, List.S.Attribute );
	List.S.Print[19-1] = LST_KLINEI;
#if ( PRO_PANEL == KANJI ) || ( PRO_JIS_CODE == ENABLE )
/*	AttributeSet( &List.S.Print[33-1], &List.S.Attribute[33-1],
		 PWRD_CipherReceiveNotice_K, LST_NOT);By Y.Suzuki 1998/04/24 */
	AttributeSet( &List.S.Print[21-1], &List.S.Attribute[21-1],
		 PWRD_CipherReceiveNotice_K, LST_NOT);
#else
	AttributeSet( &List.S.Print[20-1], &List.S.Attribute[20-1],
		 PWRD_CipherReceiveNotice, LST_MES_TWI_XXX);
#endif
	List.S.Print[61-1] = LST_KLINEI;
	if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
		return ( ret );
	}

	/*����������������������������*/
	Prn_Att_Str_Clear( List.S.Print, List.S.Attribute );
	List.S.Print[19-1] = LST_KLINE1;
	CMN_MemorySet( &List.S.Print[20-1], 41, LST_KLINE_ );
	List.S.Print[61-1] = LST_KLINE3;
	if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
		return ( ret );
	}

	/*����������������*/
	Print_CipherRecieve_List_Line( 0, List.S.Print, List.S.Attribute );
	if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
		return ( ret );
	}

	/*����M�m�����R��*/
	Print_CipherRecieve_List_Line( 1, List.S.Print, List.S.Attribute );
	for( cipher_no = 0; cipher_no < SYS_CIPHERBOX_MAX; cipher_no++ ){
		if( SYB_CipherBox[cipher_no].MessageStatus == SYS_CIPHERBOX_RCV_MSG ){
			break;
		}
	}
	CMN_UnsignedIntToASC( &List.S.Print[24-1], cipher_no, 1, '0' );
	if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
		return ( ret );
	}

	/*����������������*/
	Print_CipherRecieve_List_Line( 2, List.S.Print, List.S.Attribute );
	if ( (ret = CharacterPrint( LST_PRINT )) != OK ) {
		return ( ret );
	}

	return ( OK );
}

/*************************************************************************
	module		:[�Í�����M�ʒm���X�g�r���쐬]
	functiom	:[�Í�����M�ʒm���X�g�r�����쐬����B]
	common		:[]
	condition	:[
		�r�����
		,	0 = �������Ά�������������
		,	1 = ����M�m���D �� 	��
		,	1 = �������ކ�������������
	]
	commment	:[]
	return		:[�Ȃ�]
	machine		:[SH7043]
	language	:[SHC]
	keyword		:[LST]
	date		:[97/ 6/12]
	author		:[�K�����I]
*************************************************************************/
void	Print_CipherRecieve_List_Line(
						 UBYTE Category,			/*�r�����*/
						 register UBYTE *AscString,	/*���������*/
						 register UBYTE *AttString )/*�C��������*/
{
	Prn_Att_Str_Clear( AscString, AttString );
	switch ( Category ) {
		case 0:
			/*����������������*/
			List.S.Print[10-1]	= LST_KLINE7;
			CMN_MemorySet( &AscString[11-1], 16, LST_KLINE_ );
			AscString[22-1]	= LST_KLINE8;
			AscString[26-1] = LST_KLINE9;
			break;
		case 1:
			/*����M�m�����R��*/
			AscString[10-1]	= LST_KLINEI;
			AttributeSet( &AscString[11-1], &AttString[11-1], PWRD_ReceiveNo, LST_NOT );
			AscString[22-1]	= LST_KLINEI;
			AscString[26-1]	= LST_KLINEI;
			break;
		case 2:
			/*����������������*/
			List.S.Print[10-1]	= LST_KLINE1;
			CMN_MemorySet( &AscString[11-1], 16, LST_KLINE_ );
			AscString[22-1]	= LST_KLINE2;
			AscString[26-1] = LST_KLINE3;
			break;
	}
}

#endif	/* #if (PRO_CIPHER == ENABLE) */
