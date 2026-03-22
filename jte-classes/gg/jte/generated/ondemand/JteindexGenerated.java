package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,29,29,29,30,30,30,35,35,35,35,35,35,35,35,35,39,39,45,45,49,49,49,0,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String csrfToken, boolean isAuthenticated, String displayName) {
		jteOutput.writeContent("\r\n<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>Spring Boot Passkey Demo</title>\r\n    <style>\r\n        body { font-family: system-ui, sans-serif; padding: 2rem; }\r\n        .button {\r\n            display: inline-block;\r\n            padding: 0.5rem 1rem;\r\n            background: #0066ff;\r\n            color: white;\r\n            text-decoration: none;\r\n            border-radius: 4px;\r\n            border: none;\r\n            cursor: pointer;\r\n            font-size: 1rem;\r\n        }\r\n        .button:hover { background: #0053d6; }\r\n        .nav { margin-top: 1rem; display: flex; gap: 0.5rem; align-items: center; }\r\n    </style>\r\n</head>\r\n<body>\r\n<h1>Spring Boot Passkey Demo</h1>\r\n\r\n");
		if (isAuthenticated) {
			jteOutput.writeContent("\r\n    <p>Welcome, ");
			jteOutput.setContext("p", null);
			jteOutput.writeUserContent(displayName);
			jteOutput.writeContent("!</p>\r\n    <div class=\"nav\">\r\n        <a href=\"/profile\" class=\"button\">My Profile</a>\r\n        <a href=\"/add-passkey\" class=\"button\">Add Passkey</a>\r\n        <form action=\"/logout\" method=\"post\" style=\"display:inline;\">\r\n            <input type=\"hidden\" name=\"_csrf\"");
			var __jte_html_attribute_0 = csrfToken;
			if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
				jteOutput.writeContent(" value=\"");
				jteOutput.setContext("input", "value");
				jteOutput.writeUserContent(__jte_html_attribute_0);
				jteOutput.setContext("input", null);
				jteOutput.writeContent("\"");
			}
			jteOutput.writeContent(">\r\n            <button type=\"submit\" class=\"button\">Logout</button>\r\n        </form>\r\n    </div>\r\n");
		} else {
			jteOutput.writeContent("\r\n    <p>Welcome to the WebAuthn / Passkey demonstration.</p>\r\n    <div class=\"nav\">\r\n        <a href=\"/login\" class=\"button\">Login</a>\r\n        <a href=\"/signup\" class=\"button\">Sign Up</a>\r\n    </div>\r\n");
		}
		jteOutput.writeContent("\r\n\r\n</body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String csrfToken = (String)params.get("csrfToken");
		boolean isAuthenticated = (boolean)params.get("isAuthenticated");
		String displayName = (String)params.get("displayName");
		render(jteOutput, jteHtmlInterceptor, csrfToken, isAuthenticated, displayName);
	}
}
