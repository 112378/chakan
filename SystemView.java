package picture;

import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileSystemView;

class PFileSystemView extends FileSystemView {
	public File createNewFolder(File containingDir) throws IOException {  //ȷ���Ƿ���Ҫ��Ŀ��Ŀ��Ŀ¼������
		return null;
	}
}


//throws���÷�
//�ڿ����У����ȥ���ñ���д�ķ���ʱ���Ƿ���֪������д�ķ����Ƿ�ᷢ���쳣�����Ǻ����жϵġ�������������
//Java�������ڷ����ĺ���ʹ��throws�ؼ��ֶ��������÷����п��ܷ����쳣�������������ڵ��÷���ʱ������ȷ��֪
//���÷������쳣�����ұ����ڳ����ж��쳣���д�����������޷�ͨ����
