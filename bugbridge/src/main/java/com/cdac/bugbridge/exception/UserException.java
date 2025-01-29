package com.cdac.bugbridge.exception;

public class UserException extends  Exception{

    public UserException(String message) {
        super(message);
    }

    public static class UserAlreadyExistsException extends UserException{

        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class UserNotExistsException extends UserException{

        public UserNotExistsException(String message) {
            super(message);
        }
    }

    public static class UserValidationFailedException extends UserException{

        public UserValidationFailedException(String message) {
            super(message);
        }
    }

    public static class UserDetailsNotSufficientException extends UserException{

        public UserDetailsNotSufficientException(String message) {
            super(message);
        }
    }


}

