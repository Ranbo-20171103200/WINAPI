package me.tikiwong.PlantsVsZombies;


import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

/**
 * Windows API
 */
public interface WinAPI extends StdCallLibrary {//����һ���ӿڼ̳�StdCallLibrary�Ե��øýӿڵķ���,��StdCallLibrary��ʽ���

	WinAPI Kernel32 = (WinAPI)Native.loadLibrary("Kernel32", WinAPI.class);	//����ΪKernel32��ͨ���ú�����ȡ������̬����INSTANCE��ͨ���ó����õ��ýӿ�ʵ���Ӷ�ʹ�øýӿڷ������ڶ�������Ϊ�ýӿڵ�class����
	
	int OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId);//����򿪽��̺����������Ϊ��ӵ�еĸý��̷���Ȩ�ޣ��̳еľ�������̵�ID
//	1.����ԭ��
//	HANDLE OpenProcess(DWORD dwDesiredAccess, BOOL bInheritHandle, DWORD dwProcessId);
//	2.����
//	a.dwDesiredAccess����ӵ�еĸý��̷���Ȩ��
//	PROCESS_ALL_ACCESS  //�����ܻ�õ�Ȩ��
//	PROCESS_CREATE_PROCESS  //��Ҫ����һ������
//	PROCESS_CREATE_THREAD   //��Ҫ����һ���߳�
//	PROCESS_DUP_HANDLE      //�ظ�ʹ��DuplicateHandle���
//	PROCESS_QUERY_INFORMATION   //��ý�����Ϣ��Ȩ�ޣ��������˳����롢���ȼ�
//	PROCESS_QUERY_LIMITED_INFORMATION  /*���ĳЩ��Ϣ��Ȩ�ޣ���������PROCESS_QUERY_INFORMATION��Ҳӵ��PROCESS_QUERY_LIMITED_INFORMATIONȨ��*/
//	PROCESS_SET_INFORMATION    //����ĳЩ��Ϣ��Ȩ�ޣ���������ȼ�
//	PROCESS_SET_QUOTA          //�����ڴ����Ƶ�Ȩ�ޣ�ʹ��SetProcessWorkingSetSize
//	PROCESS_SUSPEND_RESUME     //��ͣ��ָ����̵�Ȩ��
//	PROCESS_TERMINATE          //��ֹһ�����̵�Ȩ�ޣ�ʹ��TerminateProcess
//	PROCESS_VM_OPERATION       //���������ڴ�ռ��Ȩ��(����VirtualProtectEx��WriteProcessMemory) 
//	PROCESS_VM_READ            //��ȡ�����ڴ�ռ��Ȩ�ޣ���ʹ��ReadProcessMemory
//	PROCESS_VM_WRITE           //��ȡ�����ڴ�ռ��Ȩ�ޣ���ʹ��WriteProcessMemory
//	SYNCHRONIZE                //�ȴ�������ֹ
//	b.bInheritHandle����ʾ���õ��Ľ��̾���Ƿ���Ա��̳�
//	c.dwProcessId�����򿪽��̵�PID
//	3.��������
//
//	��ɹ�������ֵΪָ�����̵ľ����
//	��ʧ�ܣ�����ֵΪNULL���ɵ���GetLastError()��ô�����롣
	int CloseHandle(int handle);//�رվ����ʾ�Ը��ں˶��󲻽����κβ�������������ʶ
	
	boolean Module32First(int hSnapshot, MODULEENTRY32 lpme);
//	BOOL WINAPI Module32First(HANDLE hSnapshot,LPMODULEENTRY32 lpme);
//			�˺������������������ĵ�һ��ģ�����Ϣ
//			����hSnapshot����CreateToolhelp32Snapshot�������صĿ��վ��
//			lpme MODULEENTRY32�ṹ��ָ�롣������������
//			����ֵ
//			�ɹ�����TRUEʧ�ܷ���FALSE
	
	boolean Module32Next(int hSnapshot, MODULEENTRY32 lpme);//�˺���������������������һ��ģ�����Ϣ
	
	int TerminateProcess(int processId,int a);//������ָֹ�����̼��������̡߳�
//	BOOL TerminateProcess(HANDLE hProcess,UINT uExitCode);
//  HANDLE hProcess //���̾��
//  UINT uExitCode //������ֹ��
//  ����ֵLong�������ʾ�ɹ������ʾʧ�ܡ�
	int ReadProcessMemory(int hProcess, int lpBaseAddress, int[] lpBuffer, int nSize, int lpNumberOfBytesRead);
//  ����ԭ��ΪBOOL ReadProcessMemory(HANDLE hProcess, LPCVOID lpBaseAddress, LPVOID lpBuffer, DWORD nSize, LPDWORD lpNumberOfBytesRead); 
//  hProcess [in]Զ�̽��̾���� ����ȡ��
//	pvAddressRemote [in]Զ�̽������ڴ��ַ�� �Ӿ���δ���ȡ
//	pvBufferLocal [out]���ؽ������ڴ��ַ. ��������ȡ������д��˴�
//	nSize [in]Ҫ���͵��ֽ�����Ҫд�����
//	pdwNumBytesRead [out]ʵ�ʴ��͵��ֽ���. ��������ʱ����ʵ��д�����
	int WriteProcessMemory(int hProcess, int lpBaseAddress, int[] lpBuffer, int nSize, int lpNumberOfBytesRead);
//	������
//	hProcess
//	��OpenProcess���صĽ��̾����
//	�����������Ϊ INVALID_HANDLE_VALUE ����-1��Ŀ�����Ϊ�������
//	lpBaseAddress
//	Ҫд���ڴ��׵�ַ
//	��д��֮ǰ���˺������ȼ��Ŀ���ַ�Ƿ���ã��������ɴ�д������ݡ�
//	lpBuffer
//	ָ��Ҫд�����ݵ�ָ�롣
//	nSize
//	Ҫд����ֽ�����
//  lpNumberOfBytesWritten,ʵ�����ݵĳ���
//	����ֵ
//	����ֵ����ɹ���
//	����GetLastError��ȡ����Ĵ�����ϸ��Ϣ��
	int CreateToolhelp32Snapshot(int falg, int id);//Ϊ��ǰ�����̴߳������գ���һ������Ϊ��ʶ
//	HANDLE WINAPI CreateToolhelp32Snapshot(DWORD dwFlags, DWORD th32ProcessID );
//�����������˼�Ǹ��뿴�Ķ����ĸ��գ�Ȼ�������Ŀ�(ϵͳ�Ľ��̻����̵߳Ĵ����ǳ���Ѹ�٣�����ֻ���ĸ���Ƭ������,�����Ĺ���Ƭ֮��ϵͳ�Ľ��̣��̣߳���ջ�ȷ����˱仯���Ͳ��ڿ��Ƿ�Χ֮���ˣ��������˵�����������ֻ���ͦ����ʵ�ʵ�) 
//			dwFlags: 
//			TH32CS_INHERIT :ʹ�������־��ʾ��������վ���ǿɼ̳е�
//
//			TH32CS_SNAPALL :��ʾʹ�������µ�ȫ����־,�ܹ��ĸ�
//          TH32CS_SNAPHEAPLIST, TH32CS_SNAPMODULE, TH32CS_SNAPPROCESS, and TH32CS_SNAPTHREAD.
//			TH32CS_SNAPHEAPLIST:��ʾ������Ϣ�����ض����̵Ķ�ջ�б�
//
//			TH32CS_SNAPMODULE :��ʾ������Ϣ�����ض����̵�ʹ��ģ����б�
//
//			TH32CS_SNAPPROCESS:��ʾ������Ϣ����ϵͳ�����н��̵��б�
//
//			TH32CS_SNAPTHREAD :��ʾ������Ϣ����ϵͳ�����̵߳��б�
//          Const TH32CS_SNAPHEAPLIST = &H1
//	        Const TH32CS_SNAPPROCESS = &H2
//			Const TH32CS_SNAPTHREAD = &H4
//			Const TH32CS_SNAPMODULE = &H8
//			Const TH32CS_SNAPALL = (TH32CS_SNAPHEAPLIST | TH32CS_SNAPPROCESS | TH32CS_SNAPTHREAD | TH32CS_SNAPMODULE)
//			Const TH32CS_INHERIT = &H80000000
//			th32ProcessID�� 
//			ֻ�е�dwFlags��Ϣ�а���TH32CS_SNAPHEAPLIST��TH32CS_SNAPMODULE ʱ���ֵ����Ч���������ֵ�ᱻ����
//			
	int Process32First(int h,PriClass p);
//	BOOL Process32First()����
//	������HANDLE hSnapshot �����Snapshot���
//	������LPPROCESSENTRY32 lppe ָ��PROCESSENTRY32�ṹ��ָ��
//	���ã���Snapshot�õ���һ�����̼�¼��Ϣ
	int Process32Next(int h,PriClass p);
//	BOOL Process32Next()����
//	������HANDLE hSnapshot �����Snapshot���
//	������LPPROCESSENTRY32 lppe ָ��PROCESSENTRY32�ṹ��ָ��
//	���ã���Snapshot�õ���һ�����̼�¼��Ϣ
}
