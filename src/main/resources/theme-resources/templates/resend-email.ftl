<#import "email-template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "title">
        ${msg("loginTitle",realm.name)}
    <#elseif section = "header">
        ${msg("email.header",realm.name)}
    <#elseif section = "form">
        <form id="kc-resend-email-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
            <div class="${properties.kcFormGroupClass!}">
                <div class="${properties.kcInputWrapperClass!}">
                    <input id="email" name="email" type="tel" class="${properties.kcInputClass!}"/>
                </div>
            </div>
            <div class="${properties.kcFormGroupClass!}">
                <div id="kc-form-options" class="${properties.kcFormOptionsClass!}">
                    <div class="${properties.kcFormOptionsWrapperClass!}">
                    </div>
                </div>

                <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                    <div class="${properties.kcFormButtonsWrapperClass!}">
                        <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"
                               name="resend_email_submit" id="kc-login" type="submit"
                               value="${msg("resend_email.submit")}"/>
                        <input class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"
                               name="resend_email_cancel" id="kc-cancel" type="submit"
                               value="${msg("resend_email.cancel")}"/>
                    </div>
                </div>
            </div>
        </form>

        <style type="text/css">
            .iti {
                width: 100%;
            }

            .iti__flag {
                background-image: url("${url.resourcesPath}/flags.png");
            }

            @media (-webkit-min-device-pixel-ratio: 2), (min-resolution: 192dpi) {
                .iti__flag {
                    background-image: url("${url.resourcesPath}/flags@2x.png");
                }
            }
        </style>

    </#if>
</@layout.registrationLayout>