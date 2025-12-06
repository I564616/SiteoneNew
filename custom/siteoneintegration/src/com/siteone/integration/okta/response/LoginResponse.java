package com.siteone.integration.okta.response;

/**
 * Created by arun on 24/9/17.
 */
public class LoginResponse {


    /**
     * expiresAt : 2015-11-03T10:15:57.000Z
     * status : SUCCESS
     * relayState : /myapp/some/deep/link/i/want/to/return/to
     * sessionToken : 00Fpzf4en68pCXTsMjcX8JPMctzN2Wiw4LDOBL_9pe
     * _embedded : {"user":{"id":"00ub0oNGTSWTBKOLGLNR","passwordChanged":"2015-09-08T20:14:45.000Z","profile":{"login":"dade.murphy@example.com","firstName":"Dade","lastName":"Murphy","locale":"en_US","timeZone":"America/Los_Angeles"}}}
     */

    private String expiresAt;
    private String status;
    private String relayState;
    private String sessionToken;
    private EmbeddedBean _embedded;

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRelayState() {
        return relayState;
    }

    public void setRelayState(String relayState) {
        this.relayState = relayState;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public EmbeddedBean get_embedded() {
        return _embedded;
    }

    public void set_embedded(EmbeddedBean _embedded) {
        this._embedded = _embedded;
    }

    public static class EmbeddedBean {
        /**
         * user : {"id":"00ub0oNGTSWTBKOLGLNR","passwordChanged":"2015-09-08T20:14:45.000Z","profile":{"login":"dade.murphy@example.com","firstName":"Dade","lastName":"Murphy","locale":"en_US","timeZone":"America/Los_Angeles"}}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 00ub0oNGTSWTBKOLGLNR
             * passwordChanged : 2015-09-08T20:14:45.000Z
             * profile : {"login":"dade.murphy@example.com","firstName":"Dade","lastName":"Murphy","locale":"en_US","timeZone":"America/Los_Angeles"}
             */

            private String id;
            private String passwordChanged;
            private ProfileBean profile;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPasswordChanged() {
                return passwordChanged;
            }

            public void setPasswordChanged(String passwordChanged) {
                this.passwordChanged = passwordChanged;
            }

            public ProfileBean getProfile() {
                return profile;
            }

            public void setProfile(ProfileBean profile) {
                this.profile = profile;
            }

            public static class ProfileBean {
                /**
                 * login : dade.murphy@example.com
                 * firstName : Dade
                 * lastName : Murphy
                 * locale : en_US
                 * timeZone : America/Los_Angeles
                 */

                private String login;
                private String firstName;
                private String lastName;
                private String locale;
                private String timeZone;

                public String getLogin() {
                    return login;
                }

                public void setLogin(String login) {
                    this.login = login;
                }

                public String getFirstName() {
                    return firstName;
                }

                public void setFirstName(String firstName) {
                    this.firstName = firstName;
                }

                public String getLastName() {
                    return lastName;
                }

                public void setLastName(String lastName) {
                    this.lastName = lastName;
                }

                public String getLocale() {
                    return locale;
                }

                public void setLocale(String locale) {
                    this.locale = locale;
                }

                public String getTimeZone() {
                    return timeZone;
                }

                public void setTimeZone(String timeZone) {
                    this.timeZone = timeZone;
                }
            }
        }
    }
}
