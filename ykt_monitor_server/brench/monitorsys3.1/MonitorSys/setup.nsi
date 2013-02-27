# Auto-generated by EclipseNSIS Script Wizard
# 2005-11-22 17:30:57

Name MonitorSystem
# Defines
!define REGKEY "SOFTWARE\$(^Name)"
!define VERSION 1.00.000
!define COMPANY ""
!define URL ""
!define SYSTEMPATH "D:\工作目录\打包工具"
!define EXECVERSION 2.0

# MUI defines
!define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\modern-uninstall.ico"
!define MUI_UNFINISHPAGE_NOAUTOCLOSE
# Included files
!include Sections.nsh
!include nsDialogs.nsh
!include MUI.nsh

# Reserved Files

# Variables

# Installer pages
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY
Page custom nsDialogsPage nsDialogsPageLeave
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

# Installer languages
!insertmacro MUI_LANGUAGE SimpChinese
#custom page varibles
Var Dialog
Var Label
Var ipserver
Var Label2
Var port
Var address_all

# Installer attributes
OutFile setup.exe
InstallDir $PROGRAMFILES\monitorsys
CRCCheck on
XPStyle on
ShowInstDetails hide
VIProductVersion 1.0.0.0
VIAddVersionKey /lang=${LANG_SIMPCHINESE} ProductName MonitorSystem
VIAddVersionKey ProductVersion "${VERSION}"
VIAddVersionKey /lang=${LANG_SIMPCHINESE} FileVersion ""
VIAddVersionKey /lang=${LANG_SIMPCHINESE} FileDescription ""
VIAddVersionKey /lang=${LANG_SIMPCHINESE} LegalCopyright ""
InstallDirRegKey HKLM "${REGKEY}" Path
SilentUnInstall normal

# Installer sections
Section -Main SEC0000
    SetOutPath $INSTDIR\mapx50
    SetOverwrite on
    File /r "${SYSTEMPATH}\MapXdll\*"
    SetOutPath $FONTS
    File /r "${SYSTEMPATH}\fonts\*"
    SetOutPath $INSTDIR\mapx50\CUSTSYMB
    File /r "${SYSTEMPATH}\CUSTSYMB\*"
    SetOutPath $INSTDIR\maps
    File /r "${SYSTEMPATH}\总图\*"
    SetOutPath $INSTDIR\iniFile
    File /r "${SYSTEMPATH}\MonitorSys.ini"
    File /r "${SYSTEMPATH}\help.chm"
    WriteRegStr HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 CommonDLLDir $INSTDIR\mapx50
    WriteRegStr HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 GeoDictionary $INSTDIR\maps\geodict.dct
    WriteRegStr HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 ProgramDir $INSTDIR\mapx50
    WriteRegStr HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 SearchPaths $INSTDIR\maps
    WriteRegStr HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 VersionCode 5.02
    WriteRegStr HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 IniFile $INSTDIR\iniFile
    WriteRegStr HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 ExeVersion ${EXECVERSION}
    WriteRegStr HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 SERVERADDRESS "$address_all"
    WriteRegStr HKLM "${REGKEY}\Components" Main 1
    SetOutPath $INSTDIR\mapx50
    RegDLL "$INSTDIR\mapx50\mapx50.dll"
    ExecShell "open" "$address_allmonitorsys.html"
SectionEnd

Section -post SEC0001
    WriteRegStr HKLM "${REGKEY}" Path $INSTDIR
    WriteUninstaller $INSTDIR\uninstall.exe
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayName "$(^Name)"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayVersion "${VERSION}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayIcon $INSTDIR\uninstall.exe
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" UninstallString $INSTDIR\uninstall.exe
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoModify 1
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoRepair 1
SectionEnd

# Macro for selecting uninstaller sections
!macro SELECT_UNSECTION SECTION_NAME UNSECTION_ID
    Push $R0
    ReadRegStr $R0 HKLM "${REGKEY}\Components" "${SECTION_NAME}"
    StrCmp $R0 1 0 next${UNSECTION_ID}
    !insertmacro SelectSection "${UNSECTION_ID}"
    Goto done${UNSECTION_ID}
next${UNSECTION_ID}:
    !insertmacro UnselectSection "${UNSECTION_ID}"
done${UNSECTION_ID}:
    Pop $R0
!macroend

# Uninstaller sections
Section /o un.Main UNSEC0000
    DeleteRegValue HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 IniFile
    DeleteRegValue HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 VersionCode
    DeleteRegValue HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 SearchPaths
    DeleteRegValue HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 ProgramDir
    DeleteRegValue HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 GeoDictionary
    DeleteRegValue HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 CommonDLLDir  
    DeleteRegValue HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 ExeVersion  
    DeleteRegValue HKEY_LOCAL_MACHINE SOFTWARE\MapInfo\MapX\5.0 SERVERADDRESS   
    RMDir /r /REBOOTOK $INSTDIR\maps
    RMDir /r /REBOOTOK $INSTDIR\mapx50\CUSTSYMB
;    RMDir /r /REBOOTOK $INSTDIR\fonts\fonts
    RMDir /r /REBOOTOK $INSTDIR\mapx50
    RMDir /r /REBOOTOK $INSTDIR\iniFile
    DeleteRegValue HKLM "${REGKEY}\Components" Main
SectionEnd

Section un.post UNSEC0001
    DeleteRegKey HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)"
    Delete /REBOOTOK $INSTDIR\uninstall.exe
    DeleteRegValue HKLM "${REGKEY}" Path
    DeleteRegKey /ifempty HKLM "${REGKEY}\Components"
    DeleteRegKey /ifempty HKLM "${REGKEY}"
    RMDir /REBOOTOK $INSTDIR
SectionEnd

# Installer functions
Function .onInit 
    ReadRegStr $0 HKLM "SOFTWARE\MapInfo\MapX\5.0" ExeVersion
    StrCmp $0 ${EXECVERSION} 0 +4 
    ReadRegStr $0 HKLM "SOFTWARE\MapInfo\MapX\5.0" SERVERADDRESS
    ExecShell "open" "$0monitorsys.html"
    Quit    
    InitPluginsDir
FunctionEnd

# Uninstaller functions
Function un.onInit
    ReadRegStr $INSTDIR HKLM "${REGKEY}" Path
    !insertmacro SELECT_UNSECTION Main ${UNSEC0000}
FunctionEnd
Function nsDialogsPage

nsDialogs::Create 1018
Pop $Dialog
${If} $Dialog == error
Abort
${EndIf}

GetDlgItem $0 $HWNDPARENT 1
EnableWindow $0 0         ; 禁用下一步按钮
        
${NSD_CreateLabel} 20u 20u 50% 12u "请输入监控web服务器ip地址!"
Pop $Label
${NSD_CreateText} 20u 34u 50% 15u "web服务器ip"
Pop $ipserver


${NSD_CreateLabel} 20u 54u 50% 12u "请输入监控服务的端口，如8080"
Pop $Label2
${NSD_CreateNumber} 20u 66u 50% 15u "8080"
Pop $port

        # 控件改变事件 及响应函数
${NSD_OnChange} $ipserver nsDialogsPageTextChange
${NSD_OnChange} $port nsDialogsPageTextChange

nsDialogs::Show

FunctionEnd

Function nsDialogsPageTextChange
${NSD_GetText} $ipserver $0
${NSD_GetText} $port $1
GetDlgItem $0 $HWNDPARENT 1
EnableWindow $0 1
FunctionEnd

Function nsDialogsPageLeave
    ${NSD_GetText} $ipserver $0
    ${NSD_GetText} $port $1
    StrCpy $address_all "http://$0:$1/monitorsys/"
FunctionEnd
