package kr.bit.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.bit.model.MemberDAO;
import kr.bit.model.MemberVo;
//6개의 pojo가 해야할 일들을 ---> 1개의 pojo로 변경
public class MemberController{//implements Controller -- X

	//MemberContentController
	@RequestMapping("/memberContent.do")
	public String memberContent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int num = Integer.parseInt(request.getParameter("num"));
		MemberDAO dao = new MemberDAO();
		MemberVo vo = dao.memberContent(num);
		
		//객체바인딩
		request.setAttribute("vo", vo);
		
		return "memberContent";//뷰의 이름만 리턴 
		//return "WEB-INF/member/memberContent.jsp";
	}
	
	//MemberDeleteController
	@RequestMapping("/memberDelete.do")
	public String memberDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ctx = request.getContextPath();

		String nextPage = null;
		int num = Integer.parseInt(request.getParameter("num"));
		MemberDAO dao = new MemberDAO();
		int count = dao.memberDelete(num);
		if (count > 0) {
			System.out.println(count + "건이 삭제되었습니다.");
			nextPage = "redirect:" + ctx + "/memberList.do";
		} else {
			throw new ServletException("not insert");
		}

		return nextPage;
	}
	
	//memberInsert
	@RequestMapping("/memberInsert.do")
	public String memberInsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String ctx = request.getContextPath();
		System.out.println(ctx);
		
		// POJO
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age")); // "40" -> 
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		//파라미터수집(VO)
		MemberVo vo = new MemberVo();
		vo.setId(id);
		vo.setPass(pass);
		vo.setName(name);
		vo.setAge(age);
		vo.setEmail(email);
		vo.setPhone(phone);
		//System.out.println(vo);//vo.toString()
		//Model과 연동부분
		MemberDAO dao = new MemberDAO();
		int count=dao.memberInsert(vo);
		String nextPage=null;

		if(count>0) {
			System.out.println("가입성공");
			//out.println("insert success");//다시 list 페이지로 돌아가야한다.(/MVC01/memberList.do)
			nextPage="redirect:"+ctx+"/memberList.do";
			
		}else {
			System.out.println("가입실패");//->예외객체를 만들어서 was에게 던지자.
			throw new ServletException("not insert");
			}
		

		return nextPage;
	}
	
	//memberList
	@RequestMapping("/memberList.do")
	public String memberList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		MemberDAO dao = new MemberDAO();
		ArrayList<MemberVo> list=dao.memberList();
		request.setAttribute("list", list);
		//member/memberList.jsp에게 주어야한다.
		
		//다음페이지
		return "memberList";

	}

	//memberRegister
	public String memberRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	
		
		
		return "memberRegister";
	}
	
	//memberUpdate
	@RequestMapping("/memberUpdate.do")
	public String memberUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String ctx = request.getContextPath();
		
		String nextPage=null;
		int num=Integer.parseInt(request.getParameter("num"));
		int age =Integer.parseInt(request.getParameter("age"));
		String email =request.getParameter("email");
		String phone =request.getParameter("phone");
		
		
		MemberVo vo =new MemberVo();
		vo.setNum(num);
		vo.setAge(age);
		vo.setEmail(email);
		vo.setPhone(phone);
		
		MemberDAO dao = new MemberDAO();
		int count=dao.memberUpdate(vo);
		if(count >0) {
			nextPage ="redirect:/"+ctx+"/memberList.do";
		}else {
			System.out.println("수정 실패");
			throw new ServletException("not insert");
		}			


		
		return nextPage;
	}

	
}
