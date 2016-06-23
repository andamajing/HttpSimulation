package com.tools.simulation.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.tools.simulation.constants.HttpResponseHeader;
import com.tools.simulation.helper.ResponseHelper;

public class SimulationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 256758765202787403L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
//		 resp.setContentType("text/html;charset=utf-8");

		String componentId = req.getParameter("componentid");
		String interfaceId = req.getParameter("interfaceid");
		Map<String, String> headers = new HashMap<String, String>();
		byte[] resultBytes = new byte[0];
		try {
			resultBytes = ResponseHelper.buildResponseBytes(componentId, interfaceId, headers);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		dealHttpResponseHeader(resp, headers);
		
		resp.setContentLength(resultBytes.length);
		ServletOutputStream stream = resp.getOutputStream();
		stream.write(resultBytes);
		stream.flush();

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private void dealHttpResponseHeader(HttpServletResponse resp, Map<String, String> headers) {
		if (MapUtils.isNotEmpty(headers)) {
			for (Entry<String, String> header : headers.entrySet()) {
				if (StringUtils.equalsIgnoreCase(header.getKey(),HttpResponseHeader.Content_Type)) {
					resp.setContentType(header.getValue());
				}
			}
		}
	}

}
