
import java.io.PrintWriter;

import com.self_j2ee.servlet.HttpServlet;
import com.self_j2ee.servlet.HttpServletRequest;
import com.self_j2ee.servlet.HttpServletResponse;

public class Hello extends HttpServlet {

	public Hello() {
		System.out.println("构造方法。。。");
	}

	@Override
	public void init() {
		super.init();
		System.out.println("初始化...");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("doGET...");
		PrintWriter out = response.getWriter();

		out.println("HTTP/1.1 200 OK\r\n\r\n");

		out.println("<html>");
		out.println("<body>");
		out.println("成功");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("doPost...");
	}

}
