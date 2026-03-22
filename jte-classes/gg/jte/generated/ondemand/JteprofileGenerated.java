package gg.jte.generated.ondemand;
import org.springframework.security.web.webauthn.api.CredentialRecord;
import java.util.List;
@SuppressWarnings("unchecked")
public final class JteprofileGenerated {
	public static final String JTE_NAME = "profile.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,3,18,18,18,18,22,22,24,24,25,25,27,27,27,28,28,28,29,29,29,31,31,32,32,39,39,39,39,39,39,39,39,39,56,56,56,71,71,71,3,4,5,5,5,5};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String username, String csrfToken, List<CredentialRecord> keys) {
		jteOutput.writeContent("\r\n<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>User Profile</title>\r\n    <style>\r\n        body { font-family: sans-serif; padding: 2rem; }\r\n        .key-card { border: 1px solid #ccc; padding: 1rem; margin-bottom: 1rem; border-radius: 8px; }\r\n    </style>\r\n</head>\r\n<body>\r\n<h1>Welcome, ");
		jteOutput.setContext("h1", null);
		jteOutput.writeUserContent(username);
		jteOutput.writeContent("!</h1>\r\n\r\n<h2>Your Registered Passkeys</h2>\r\n\r\n");
		if (keys.isEmpty()) {
			jteOutput.writeContent("\r\n    <p>You have no passkeys registered yet.</p>\r\n");
		} else {
			jteOutput.writeContent("\r\n    ");
			for (var key : keys) {
				jteOutput.writeContent("\r\n        <div class=\"key-card\">\r\n            <strong>Label:</strong> ");
				jteOutput.setContext("div", null);
				jteOutput.writeUserContent(key.getLabel());
				jteOutput.writeContent("<br>\r\n            <strong>Created:</strong> ");
				jteOutput.setContext("div", null);
				jteOutput.writeUserContent(key.getCreated().toString());
				jteOutput.writeContent("<br>\r\n            <strong>Credential ID:</strong> <small>");
				jteOutput.setContext("small", null);
				jteOutput.writeUserContent(key.getCredentialId().toString());
				jteOutput.writeContent("</small>\r\n        </div>\r\n    ");
			}
			jteOutput.writeContent("\r\n");
		}
		jteOutput.writeContent("\r\n\r\n<hr>\r\n<div style=\"display: flex; gap: 10px; align-items: center; margin-bottom: 20px;\">\r\n    <a href=\"/\" style=\"text-decoration: none; color: #0066ff;\">Back to Home</a>\r\n    <button onclick=\"createDeviceLink()\" style=\"display: inline-block; padding: 0.5rem 1rem; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer;\">Link New Device</button>\r\n    <form action=\"/logout\" method=\"post\" style=\"display:inline;\">\r\n        <input type=\"hidden\" name=\"_csrf\"");
		var __jte_html_attribute_0 = csrfToken;
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" value=\"");
			jteOutput.setContext("input", "value");
			jteOutput.writeUserContent(__jte_html_attribute_0);
			jteOutput.setContext("input", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(">\r\n        <button type=\"submit\" style=\"padding: 0.5rem 1rem; background: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer;\">Logout</button>\r\n    </form>\r\n</div>\r\n\r\n<div id=\"link-container\" style=\"margin-top: 10px; display: none; padding: 1rem; border: 1px solid #28a745; border-radius: 8px; background: #f0fff0;\">\r\n    <p style=\"margin-top: 0;\"><strong>Device Link Created!</strong></p>\r\n    <p>Use this link on your other device or browser to add a passkey:</p>\r\n    <code id=\"device-link-url\" style=\"display: block; background: #fff; padding: 10px; border: 1px solid #ddd; margin-bottom: 10px; word-break: break-all;\"></code>\r\n    <a id=\"device-link-href\" href=\"#\" style=\"display: inline-block; padding: 0.4rem 0.8rem; background: #007bff; color: white; text-decoration: none; border-radius: 4px;\">Open link here</a>\r\n</div>\r\n\r\n<script>\r\n    async function createDeviceLink() {\r\n        const response = await fetch('/device-link/create', {\r\n            method: 'POST',\r\n            headers: {\r\n                'X-CSRF-TOKEN': '");
		jteOutput.setContext("script", null);
		jteOutput.writeUserContent(csrfToken);
		jteOutput.writeContent("'\r\n            }\r\n        });\r\n        if (response.ok) {\r\n            const data = await response.json();\r\n            document.getElementById('device-link-url').innerText = data.url;\r\n            document.getElementById('device-link-href').href = data.url;\r\n            document.getElementById('link-container').style.display = 'block';\r\n        } else {\r\n            alert('Failed to create device link');\r\n        }\r\n    }\r\n</script>\r\n</body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String username = (String)params.get("username");
		String csrfToken = (String)params.get("csrfToken");
		List<CredentialRecord> keys = (List<CredentialRecord>)params.get("keys");
		render(jteOutput, jteHtmlInterceptor, username, csrfToken, keys);
	}
}
