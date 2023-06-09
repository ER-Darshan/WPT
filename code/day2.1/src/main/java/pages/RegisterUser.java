package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDaoImpl;
import pojos.User;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/register")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDaoImpl userDao;
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			// create dao instance
			userDao = new UserDaoImpl();
		} catch (Exception e) {
			// re throw the exc to the caller (WC) , so that WC DOES NOT continue with the
			// rest of the servlet life cycle
			// ServletException(string errmsg,Throwable rootCause)
			throw new ServletException("err in init of " + getClass(), e);// if not written the wc will think all is
																			// good
		}
		}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("register Servlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		try(PrintWriter pw=response.getWriter()){
			String fname = request.getParameter("fnm");
			System.out.println(fname);
			String lname = request.getParameter("lnm");
			String dob1 = request.getParameter("dob");
			String email = request.getParameter("em");
			String pwd = request.getParameter("pass");
			String user = userDao.registerNewUser(fname,lname,dob1,email, pwd);
			pw.print("Thank yu for reg"+user);
		}catch (Exception e) {
			throw new ServletException("err in do-get"+getClass(),e);
		}
	}

}
