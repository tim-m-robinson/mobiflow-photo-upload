package org.tmr.jamm;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.tmr.rest.PhotoUploadForm;

import javax.enterprise.context.ApplicationScoped;
import javax.ejb.Startup;

/**
 * Created by tmr
 */
@ApplicationScoped
@Startup
@ContextName("MobiflowCamel")
public class EchoRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:echo")
            .convertBodyTo(PhotoUploadForm.class)
            .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    PhotoUploadForm form = exchange.getIn().getBody(PhotoUploadForm.class);
                    form.hasPassedCamel = true;
                    exchange.getOut().setBody(form, PhotoUploadForm.class);
                }
            })
            .end();
    }
}
