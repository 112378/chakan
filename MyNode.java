package picture;

import java.io.File;
import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;

public class MyNode extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean explored_ = false;

	public MyNode(File file) {
		setUserObject(file);
	}

	public boolean getAllowChildren() {
		return isDirectory();
	}

	public boolean isLeaf() {
		return !isDirectory();
	}

	public File getFile() {
		return (File) getUserObject();
	}

	public boolean isExplored() {
		return explored_;
	}

	public boolean isDirectory() {
		File file = getFile();
		return file.isDirectory();
	}

	public String toString() {
		File file = getFile();
		String filename = file.toString();
		int index = filename.lastIndexOf("\\");
		return (index != -1 && index != filename.length() - 1) ? filename.substring(index + 1) : filename;
	}

	public String getString() {
		File file = getFile();
		String filename = file.getAbsolutePath();
		return filename;
	}

	public File getWorR() {
		File file = getFile();
		File file1 = file.getAbsoluteFile();
		return file1;
	}

	public String getWorR1() throws IOException {
		File file = getFile();
		String file1 = file.getPath();
		return file1;
	}

	public void explore() {
		if (!isDirectory()) {  //是检查一个对象是否是文件夹，返回值是boolean类型的
			return;
		}
		if (!isExplored()) {
			File file = getFile();
			File[] children = file.listFiles();
			for (int i = 0; i < children.length; ++i) {
				if (children[i].isDirectory()) {
					add(new MyNode(children[i]));
				}
			}
			explored_ = true;
		}
	}
}
