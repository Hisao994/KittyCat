import java.io.PrintWriter;

import com.self_j2ee.servlet.HttpServlet;
import com.self_j2ee.servlet.HttpServletRequest;
import com.self_j2ee.servlet.HttpServletResponse;
import com.self_j2ee.servlet.HttpSession;

public class Hello2 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		String username = (String) session.getAttribute("username");
		String email = (String) session.getAttribute("email");
		String msg = (String) session.getAttribute("msg");

		System.out.println(username + "\t" + email + "\t" + msg);
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

}
