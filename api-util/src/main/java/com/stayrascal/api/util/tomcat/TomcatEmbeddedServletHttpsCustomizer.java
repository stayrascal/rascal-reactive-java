package com.stayrascal.api.util.tomcat;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

public class TomcatEmbeddedServletHttpsCustomizer implements EmbeddedServletContainerCustomizer {

    public static final String KEYSTORE_TYPE = "JKS";
    public static final String SCHEME = "https";
    public static final String SSL_CIPHERS = "";
    public static final String SERVER = "Unknown";
    public static final String TLSV1_2 = "TLSv1.2";
    public static final String TLSV1 = "TLSV1";

    private int httpsPort;
    private String keystoreFile;
    private String keystorePassword;
    private String keystoreAlias;
    private boolean paranoiaMode;

    public TomcatEmbeddedServletHttpsCustomizer(int httpsPort, String keystoreFile, String keystorePassword, String keystoreAlias) {
        this(httpsPort, keystoreFile, keystorePassword, keystoreAlias, true);
    }

    public TomcatEmbeddedServletHttpsCustomizer(int httpsPort, String keystoreFile, String keystorePassword, String keystoreAlias, boolean paranoiaMode) {
        this.httpsPort = httpsPort;
        this.keystoreFile = keystoreFile;
        this.keystorePassword = keystorePassword;
        this.keystoreAlias = keystoreAlias;
        this.paranoiaMode = paranoiaMode;
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
        tomcat.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setPort(httpsPort);
                connector.setSecure(true);
                connector.setScheme(SCHEME);
                setupProtocolHandler(connector);
            }

            private void setupProtocolHandler(Connector connector) {
                Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
                proto.setSSLEnabled(true);
                proto.setKeystoreFile(keystoreFile);
                proto.setKeystorePass(keystorePassword);
                proto.setKeystoreType(KEYSTORE_TYPE);
                proto.setKeyAlias(keystoreAlias);
                proto.setSslProtocol(TLSV1);
                if (paranoiaMode) {
                    proto.setCiphers(SSL_CIPHERS);
                    proto.setSslProtocol(TLSV1_2);
                    proto.setServer(SERVER);
                }
            }
        });

    }
}
