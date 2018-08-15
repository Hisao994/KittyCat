import java.io.PrintWriter;
import java.util.Map;

import com.self_j2ee.servlet.HttpServlet;
import com.self_j2ee.servlet.HttpServletRequest;
import com.self_j2ee.servlet.HttpServletResponse;
import com.self_j2ee.servlet.HttpSession;

public class Guest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String op = request.getParameter("op");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String msg = request.getParameter("msg");

		Map<String, String> headers = request.getHeaders();
		System.out.println(headers);

		System.out.println(request.getCookies());

		System.out.println(op + "  " + username + "  " + email + "\t" + msg);

		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("email", email);
		session.setAttribute("msg", msg);

		PrintWriter out = response.getWriter();
		// out.println("HTTP/1.1 200 OK\r\n\r\n");
		String jsessionid = request.getJsessionid();
		if (jsessionid != null) {
			out.println("HTTP/1.1 200 OK\r\nSet-Cookie: JSESSIONID=" + jsessionid + "\r\n\r\n");
		} else {
			out.println("HTTP/1.1 200 OK\r\n\r\n");
		}
		out.println("<html>");
		out.println("<body>");
		out.println("添加成功");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

}
