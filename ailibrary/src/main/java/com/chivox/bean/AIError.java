package com.chivox.bean;

/**
 * author by Mckiera
 * time: 2016/3/3  15:17
 * description:
 * updateTime:
 * update description:
 */
public class AIError {
    /**
     * applicationId : 14339153910000df
     * dtLastResponse : 2016-03-03 15:15:04:441
     * eof : 1
     * errId : 51000
     * error : start the core unsuccessfully
     * params : {"app":{"applicationId":"14339153910000df","clientId":"1c450b30ff500e77","sig":"2d6ce1fa02e24e3b90180c5c9471f9f31c4db1c0","timestamp":"1456989304","userId":"160125318950"},"audio":{"audioType":"ogg","channel":1,"sampleBytes":2,"sampleRate":16000},"request":{"attachAudioUrl":1,"coreType":"en.sent.score","rank":100,"refText":"one(large) bowl of...","tokenId":"56d7e4783327935cb4000001"}}
     * refText : one(large) bowl of...
     * tokenId : 56d7e4783327935cb4000001
     */

    private String applicationId;
    private String dtLastResponse;
    private int eof;
    private int errId;
    private String error;
    /**
     * app : {"applicationId":"14339153910000df","clientId":"1c450b30ff500e77","sig":"2d6ce1fa02e24e3b90180c5c9471f9f31c4db1c0","timestamp":"1456989304","userId":"160125318950"}
     * audio : {"audioType":"ogg","channel":1,"sampleBytes":2,"sampleRate":16000}
     * request : {"attachAudioUrl":1,"coreType":"en.sent.score","rank":100,"refText":"one(large) bowl of...","tokenId":"56d7e4783327935cb4000001"}
     */

    private ParamsEntity params;
    private String refText;
    private String tokenId;

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public void setDtLastResponse(String dtLastResponse) {
        this.dtLastResponse = dtLastResponse;
    }

    public void setEof(int eof) {
        this.eof = eof;
    }

    public void setErrId(int errId) {
        this.errId = errId;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setParams(ParamsEntity params) {
        this.params = params;
    }

    public void setRefText(String refText) {
        this.refText = refText;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getDtLastResponse() {
        return dtLastResponse;
    }

    public int getEof() {
        return eof;
    }

    public int getErrId() {
        return errId;
    }

    public String getError() {
        return error;
    }

    public ParamsEntity getParams() {
        return params;
    }

    public String getRefText() {
        return refText;
    }

    public String getTokenId() {
        return tokenId;
    }

    public static class ParamsEntity {
        /**
         * applicationId : 14339153910000df
         * clientId : 1c450b30ff500e77
         * sig : 2d6ce1fa02e24e3b90180c5c9471f9f31c4db1c0
         * timestamp : 1456989304
         * userId : 160125318950
         */

        private AppEntity app;
        /**
         * audioType : ogg
         * channel : 1
         * sampleBytes : 2
         * sampleRate : 16000
         */

        private AudioEntity audio;
        /**
         * attachAudioUrl : 1
         * coreType : en.sent.score
         * rank : 100
         * refText : one(large) bowl of...
         * tokenId : 56d7e4783327935cb4000001
         */

        private RequestEntity request;

        public void setApp(AppEntity app) {
            this.app = app;
        }

        public void setAudio(AudioEntity audio) {
            this.audio = audio;
        }

        public void setRequest(RequestEntity request) {
            this.request = request;
        }

        public AppEntity getApp() {
            return app;
        }

        public AudioEntity getAudio() {
            return audio;
        }

        public RequestEntity getRequest() {
            return request;
        }

        public static class AppEntity {
            private String applicationId;
            private String clientId;
            private String sig;
            private String timestamp;
            private String userId;

            public void setApplicationId(String applicationId) {
                this.applicationId = applicationId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public void setSig(String sig) {
                this.sig = sig;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getApplicationId() {
                return applicationId;
            }

            public String getClientId() {
                return clientId;
            }

            public String getSig() {
                return sig;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public String getUserId() {
                return userId;
            }
        }

        public static class AudioEntity {
            private String audioType;
            private int channel;
            private int sampleBytes;
            private int sampleRate;

            public void setAudioType(String audioType) {
                this.audioType = audioType;
            }

            public void setChannel(int channel) {
                this.channel = channel;
            }

            public void setSampleBytes(int sampleBytes) {
                this.sampleBytes = sampleBytes;
            }

            public void setSampleRate(int sampleRate) {
                this.sampleRate = sampleRate;
            }

            public String getAudioType() {
                return audioType;
            }

            public int getChannel() {
                return channel;
            }

            public int getSampleBytes() {
                return sampleBytes;
            }

            public int getSampleRate() {
                return sampleRate;
            }
        }

        public static class RequestEntity {
            private int attachAudioUrl;
            private String coreType;
            private int rank;
            private String refText;
            private String tokenId;

            public void setAttachAudioUrl(int attachAudioUrl) {
                this.attachAudioUrl = attachAudioUrl;
            }

            public void setCoreType(String coreType) {
                this.coreType = coreType;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public void setRefText(String refText) {
                this.refText = refText;
            }

            public void setTokenId(String tokenId) {
                this.tokenId = tokenId;
            }

            public int getAttachAudioUrl() {
                return attachAudioUrl;
            }

            public String getCoreType() {
                return coreType;
            }

            public int getRank() {
                return rank;
            }

            public String getRefText() {
                return refText;
            }

            public String getTokenId() {
                return tokenId;
            }
        }
    }
}
