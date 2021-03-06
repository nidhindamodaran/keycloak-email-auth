# keycloak-email-auth

Email authenticator plugin for keycloak. Inspired from https://github.com/spiashko/keycloak-sms-authenticator

### Installation

Execute `mvn clean install` from the project path. This is build jar file (keycloak-email-auth-<version>.jar) in targets folder

Add the jar with dependencies to the Keycloak server:
  * `$ cp target/<jar file with dependecy>.jar _KEYCLOAK_HOME_/standalone/deployments/`

Add below environment variables for SMTP configuration
- EMAIL_PASSWORD
- EMAIL_USERNAME
- FROM_EMAIL
- SMTP_HOST
- SMTP_PORT

### Configuration

Configuring realm in kaycloak to use email authentication

First create a new REALM (or select a previously created REALM).

Under Authentication > Flows:
* Copy 'Browse' flow to 'Browser with Email' flow
* Click on 'Actions > Add execution on the 'Browser with Email Forms' line and add the 'Email Authentication'
* Set 'Email Authentication' to 'REQUIRED'

Under Authentication > Bindings:
* Select 'Browser with Email' as the 'Browser Flow' for the REALM.

Make sure that all users has `email` configured.
