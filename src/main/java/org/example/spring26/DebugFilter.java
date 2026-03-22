package org.example.spring26;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DebugFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getRequestURI().equals("/login/webauthn")) {
            byte[] body = httpRequest.getInputStream().readAllBytes();
            System.out.println("=== /login/webauthn ===");
            System.out.println("Content-Type: " + httpRequest.getContentType());
            System.out.println("Body length: " + body.length);
            System.out.println("Body: " + new String(body));
            System.out.println("Body byte 0: " + String.format("0x%02X", body[0]));

            // Wrap the request so Spring can still read the body
            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {
                @Override
                public ServletInputStream getInputStream() {
                    ByteArrayInputStream bais = new ByteArrayInputStream(body);
                    return new ServletInputStream() {
                        public int read() {
                            return bais.read();
                        }

                        public boolean isFinished() {
                            return bais.available() == 0;
                        }

                        public boolean isReady() {
                            return true;
                        }

                        public void setReadListener(ReadListener l) {
                        }
                    };
                }
            };
            chain.doFilter(wrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
