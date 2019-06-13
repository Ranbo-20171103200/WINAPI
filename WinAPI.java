package me.tikiwong.PlantsVsZombies;


import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

/**
 * Windows API
 */
public interface WinAPI extends StdCallLibrary {//定义一个接口继承StdCallLibrary以调用该接口的方法,以StdCallLibrary方式输出

	WinAPI Kernel32 = (WinAPI)Native.loadLibrary("Kernel32", WinAPI.class);	//库名为Kernel32，通过该函数获取公共静态常量INSTANCE，通过该常量得到该接口实例从而使用该接口方法，第二个参数为该接口的class类型
	
	int OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId);//定义打开进程函数传入参数为想拥有的该进程访问权限，继承的句柄，进程的ID
//	1.函数原型
//	HANDLE OpenProcess(DWORD dwDesiredAccess, BOOL bInheritHandle, DWORD dwProcessId);
//	2.参数
//	a.dwDesiredAccess：想拥有的该进程访问权限
//	PROCESS_ALL_ACCESS  //所有能获得的权限
//	PROCESS_CREATE_PROCESS  //需要创建一个进程
//	PROCESS_CREATE_THREAD   //需要创建一个线程
//	PROCESS_DUP_HANDLE      //重复使用DuplicateHandle句柄
//	PROCESS_QUERY_INFORMATION   //获得进程信息的权限，如它的退出代码、优先级
//	PROCESS_QUERY_LIMITED_INFORMATION  /*获得某些信息的权限，如果获得了PROCESS_QUERY_INFORMATION，也拥有PROCESS_QUERY_LIMITED_INFORMATION权限*/
//	PROCESS_SET_INFORMATION    //设置某些信息的权限，如进程优先级
//	PROCESS_SET_QUOTA          //设置内存限制的权限，使用SetProcessWorkingSetSize
//	PROCESS_SUSPEND_RESUME     //暂停或恢复进程的权限
//	PROCESS_TERMINATE          //终止一个进程的权限，使用TerminateProcess
//	PROCESS_VM_OPERATION       //操作进程内存空间的权限(可用VirtualProtectEx和WriteProcessMemory) 
//	PROCESS_VM_READ            //读取进程内存空间的权限，可使用ReadProcessMemory
//	PROCESS_VM_WRITE           //读取进程内存空间的权限，可使用WriteProcessMemory
//	SYNCHRONIZE                //等待进程终止
//	b.bInheritHandle：表示所得到的进程句柄是否可以被继承
//	c.dwProcessId：被打开进程的PID
//	3.返回类型
//
//	如成功，返回值为指定进程的句柄。
//	如失败，返回值为NULL，可调用GetLastError()获得错误代码。
	int CloseHandle(int handle);//关闭句柄表示对该内核对象不进行任何操作，传入句柄标识
	
	boolean Module32First(int hSnapshot, MODULEENTRY32 lpme);
//	BOOL WINAPI Module32First(HANDLE hSnapshot,LPMODULEENTRY32 lpme);
//			此函数检索与进程相关联的第一个模块的信息
//			参数hSnapshot调用CreateToolhelp32Snapshot函数返回的快照句柄
//			lpme MODULEENTRY32结构的指针。用来返回数据
//			返回值
//			成功返回TRUE失败返回FALSE
	
	boolean Module32Next(int hSnapshot, MODULEENTRY32 lpme);//此函数检索与进程相关联的下一个模块的信息
	
	int TerminateProcess(int processId,int a);//函数终止指定进程及其所有线程。
//	BOOL TerminateProcess(HANDLE hProcess,UINT uExitCode);
//  HANDLE hProcess //进程句柄
//  UINT uExitCode //进程终止码
//  返回值Long，非零表示成功，零表示失败。
	int ReadProcessMemory(int hProcess, int lpBaseAddress, int[] lpBuffer, int nSize, int lpNumberOfBytesRead);
//  函数原型为BOOL ReadProcessMemory(HANDLE hProcess, LPCVOID lpBaseAddress, LPVOID lpBuffer, DWORD nSize, LPDWORD lpNumberOfBytesRead); 
//  hProcess [in]远程进程句柄。 被读取者
//	pvAddressRemote [in]远程进程中内存地址。 从具体何处读取
//	pvBufferLocal [out]本地进程中内存地址. 函数将读取的内容写入此处
//	nSize [in]要传送的字节数。要写入多少
//	pdwNumBytesRead [out]实际传送的字节数. 函数返回时报告实际写入多少
	int WriteProcessMemory(int hProcess, int lpBaseAddress, int[] lpBuffer, int nSize, int lpNumberOfBytesRead);
//	参数：
//	hProcess
//	由OpenProcess返回的进程句柄。
//	如参数传数据为 INVALID_HANDLE_VALUE 【即-1】目标进程为自身进程
//	lpBaseAddress
//	要写的内存首地址
//	在写入之前，此函数将先检查目标地址是否可用，并能容纳待写入的数据。
//	lpBuffer
//	指向要写的数据的指针。
//	nSize
//	要写入的字节数。
//  lpNumberOfBytesWritten,实际数据的长度
//	返回值
//	非零值代表成功。
//	可用GetLastError获取更多的错误详细信息。
	int CreateToolhelp32Snapshot(int falg, int id);//为当前进程线程创建快照，第一个参数为标识
//	HANDLE WINAPI CreateToolhelp32Snapshot(DWORD dwFlags, DWORD th32ProcessID );
//这个函数的意思是给想看的东西拍个照，然后慢慢的看(系统的进程或者线程的创建非常的迅速，所以只能拍个照片慢慢看,若是拍过照片之后，系统的进程，线程，堆栈等发生了变化，就不在考虑范围之内了，从这点来说，函数的名字还是挺贴合实际的) 
//			dwFlags: 
//			TH32CS_INHERIT :使用这个标志表示，这个快照句柄是可继承的
//
//			TH32CS_SNAPALL :表示使用了以下的全部标志,总共四个
//          TH32CS_SNAPHEAPLIST, TH32CS_SNAPMODULE, TH32CS_SNAPPROCESS, and TH32CS_SNAPTHREAD.
//			TH32CS_SNAPHEAPLIST:表示快照信息包含特定进程的堆栈列表
//
//			TH32CS_SNAPMODULE :表示快照信息包含特定进程的使用模块的列表
//
//			TH32CS_SNAPPROCESS:表示快照信息包含系统的所有进程的列表
//
//			TH32CS_SNAPTHREAD :表示快照信息包含系统所有线程的列表
//          Const TH32CS_SNAPHEAPLIST = &H1
//	        Const TH32CS_SNAPPROCESS = &H2
//			Const TH32CS_SNAPTHREAD = &H4
//			Const TH32CS_SNAPMODULE = &H8
//			Const TH32CS_SNAPALL = (TH32CS_SNAPHEAPLIST | TH32CS_SNAPPROCESS | TH32CS_SNAPTHREAD | TH32CS_SNAPMODULE)
//			Const TH32CS_INHERIT = &H80000000
//			th32ProcessID： 
//			只有当dwFlags信息中包含TH32CS_SNAPHEAPLIST，TH32CS_SNAPMODULE 时这个值才有效，否则，这个值会被忽略
//			
	int Process32First(int h,PriClass p);
//	BOOL Process32First()函数
//	参数：HANDLE hSnapshot 传入的Snapshot句柄
//	参数：LPPROCESSENTRY32 lppe 指向PROCESSENTRY32结构的指针
//	作用：从Snapshot得到第一个进程记录信息
	int Process32Next(int h,PriClass p);
//	BOOL Process32Next()函数
//	参数：HANDLE hSnapshot 传入的Snapshot句柄
//	参数：LPPROCESSENTRY32 lppe 指向PROCESSENTRY32结构的指针
//	作用：从Snapshot得到下一个进程记录信息
}
