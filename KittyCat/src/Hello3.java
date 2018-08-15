import java.io.PrintWriter;

import com.self_j2ee.servlet.HttpServlet;
import com.self_j2ee.servlet.HttpServletRequest;
import com.self_j2ee.servlet.HttpServletResponse;

public class Hello3 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getSession().getId();
		System.out.println(id);
		PrintWriter out = response.getWriter();

		out.println("HTTP/1.1 200 OK\r\n\r\n");

		out.println("<html>");
		out.println("<body>");
		out.println("成功");
		out.println("id	" + id);
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

}
