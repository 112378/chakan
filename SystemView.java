package picture;

import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileSystemView;

class PFileSystemView extends FileSystemView {
	public File createNewFolder(File containingDir) throws IOException {  //确定是否检查要项目的目标目录不存在
		return null;
	}
}


//throws的用法
//在开发中，如果去调用别人写的方法时，是否能知道别人写的方法是否会发生异常？这是很难判断的。针对这种情况，
//Java总允许在方法的后面使用throws关键字对外声明该方法有可能发生异常，这样调用者在调用方法时，就明确地知
//道该方法有异常，并且必须在程序中对异常进行处理，否则编译无法通过。
