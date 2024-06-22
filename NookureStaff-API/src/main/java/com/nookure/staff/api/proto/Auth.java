// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: auth.proto
// Protobuf Java Version: 4.27.1

package com.nookure.staff.api.proto;

public final class Auth {
  private Auth() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface AuthRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.nookure.staff.api.proto.AuthRequest)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>string username = 1;</code>
     * @return The username.
     */
    java.lang.String getUsername();
    /**
     * <code>string username = 1;</code>
     * @return The bytes for username.
     */
    com.google.protobuf.ByteString
        getUsernameBytes();

    /**
     * <code>string otp = 2;</code>
     * @return The otp.
     */
    java.lang.String getOtp();
    /**
     * <code>string otp = 2;</code>
     * @return The bytes for otp.
     */
    com.google.protobuf.ByteString
        getOtpBytes();
  }
  /**
   * Protobuf type {@code com.nookure.staff.api.proto.AuthRequest}
   */
  public  static final class AuthRequest extends
      com.google.protobuf.GeneratedMessageLite<
          AuthRequest, AuthRequest.Builder> implements
      // @@protoc_insertion_point(message_implements:com.nookure.staff.api.proto.AuthRequest)
      AuthRequestOrBuilder {
    private AuthRequest() {
      username_ = "";
      otp_ = "";
    }
    public static final int USERNAME_FIELD_NUMBER = 1;
    private java.lang.String username_;
    /**
     * <code>string username = 1;</code>
     * @return The username.
     */
    @java.lang.Override
    public java.lang.String getUsername() {
      return username_;
    }
    /**
     * <code>string username = 1;</code>
     * @return The bytes for username.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getUsernameBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(username_);
    }
    /**
     * <code>string username = 1;</code>
     * @param value The username to set.
     */
    private void setUsername(
        java.lang.String value) {
      java.lang.Class<?> valueClass = value.getClass();
  
      username_ = value;
    }
    /**
     * <code>string username = 1;</code>
     */
    private void clearUsername() {

      username_ = getDefaultInstance().getUsername();
    }
    /**
     * <code>string username = 1;</code>
     * @param value The bytes for username to set.
     */
    private void setUsernameBytes(
        com.google.protobuf.ByteString value) {
      checkByteStringIsUtf8(value);
      username_ = value.toStringUtf8();

    }

    public static final int OTP_FIELD_NUMBER = 2;
    private java.lang.String otp_;
    /**
     * <code>string otp = 2;</code>
     * @return The otp.
     */
    @java.lang.Override
    public java.lang.String getOtp() {
      return otp_;
    }
    /**
     * <code>string otp = 2;</code>
     * @return The bytes for otp.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getOtpBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(otp_);
    }
    /**
     * <code>string otp = 2;</code>
     * @param value The otp to set.
     */
    private void setOtp(
        java.lang.String value) {
      java.lang.Class<?> valueClass = value.getClass();
  
      otp_ = value;
    }
    /**
     * <code>string otp = 2;</code>
     */
    private void clearOtp() {

      otp_ = getDefaultInstance().getOtp();
    }
    /**
     * <code>string otp = 2;</code>
     * @param value The bytes for otp to set.
     */
    private void setOtpBytes(
        com.google.protobuf.ByteString value) {
      checkByteStringIsUtf8(value);
      otp_ = value.toStringUtf8();

    }

    public static com.nookure.staff.api.proto.Auth.AuthRequest parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static com.nookure.staff.api.proto.Auth.AuthRequest parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static com.nookure.staff.api.proto.Auth.AuthRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static com.nookure.staff.api.proto.Auth.AuthRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static com.nookure.staff.api.proto.Auth.AuthRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static com.nookure.staff.api.proto.Auth.AuthRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static com.nookure.staff.api.proto.Auth.AuthRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static com.nookure.staff.api.proto.Auth.AuthRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static com.nookure.staff.api.proto.Auth.AuthRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static com.nookure.staff.api.proto.Auth.AuthRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static com.nookure.staff.api.proto.Auth.AuthRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static com.nookure.staff.api.proto.Auth.AuthRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(com.nookure.staff.api.proto.Auth.AuthRequest prototype) {
      return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code com.nookure.staff.api.proto.AuthRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          com.nookure.staff.api.proto.Auth.AuthRequest, Builder> implements
        // @@protoc_insertion_point(builder_implements:com.nookure.staff.api.proto.AuthRequest)
        com.nookure.staff.api.proto.Auth.AuthRequestOrBuilder {
      // Construct using com.nookure.staff.api.proto.Auth.AuthRequest.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>string username = 1;</code>
       * @return The username.
       */
      @java.lang.Override
      public java.lang.String getUsername() {
        return instance.getUsername();
      }
      /**
       * <code>string username = 1;</code>
       * @return The bytes for username.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getUsernameBytes() {
        return instance.getUsernameBytes();
      }
      /**
       * <code>string username = 1;</code>
       * @param value The username to set.
       * @return This builder for chaining.
       */
      public Builder setUsername(
          java.lang.String value) {
        copyOnWrite();
        instance.setUsername(value);
        return this;
      }
      /**
       * <code>string username = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearUsername() {
        copyOnWrite();
        instance.clearUsername();
        return this;
      }
      /**
       * <code>string username = 1;</code>
       * @param value The bytes for username to set.
       * @return This builder for chaining.
       */
      public Builder setUsernameBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setUsernameBytes(value);
        return this;
      }

      /**
       * <code>string otp = 2;</code>
       * @return The otp.
       */
      @java.lang.Override
      public java.lang.String getOtp() {
        return instance.getOtp();
      }
      /**
       * <code>string otp = 2;</code>
       * @return The bytes for otp.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getOtpBytes() {
        return instance.getOtpBytes();
      }
      /**
       * <code>string otp = 2;</code>
       * @param value The otp to set.
       * @return This builder for chaining.
       */
      public Builder setOtp(
          java.lang.String value) {
        copyOnWrite();
        instance.setOtp(value);
        return this;
      }
      /**
       * <code>string otp = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearOtp() {
        copyOnWrite();
        instance.clearOtp();
        return this;
      }
      /**
       * <code>string otp = 2;</code>
       * @param value The bytes for otp to set.
       * @return This builder for chaining.
       */
      public Builder setOtpBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setOtpBytes(value);
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.nookure.staff.api.proto.AuthRequest)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new com.nookure.staff.api.proto.Auth.AuthRequest();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "username_",
              "otp_",
            };
            java.lang.String info =
                "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0208\u0002\u0208" +
                "";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<com.nookure.staff.api.proto.Auth.AuthRequest> parser = PARSER;
          if (parser == null) {
            synchronized (com.nookure.staff.api.proto.Auth.AuthRequest.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<com.nookure.staff.api.proto.Auth.AuthRequest>(
                        DEFAULT_INSTANCE);
                PARSER = parser;
              }
            }
          }
          return parser;
      }
      case GET_MEMOIZED_IS_INITIALIZED: {
        return (byte) 1;
      }
      case SET_MEMOIZED_IS_INITIALIZED: {
        return null;
      }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:com.nookure.staff.api.proto.AuthRequest)
    private static final com.nookure.staff.api.proto.Auth.AuthRequest DEFAULT_INSTANCE;
    static {
      AuthRequest defaultInstance = new AuthRequest();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        AuthRequest.class, defaultInstance);
    }

    public static com.nookure.staff.api.proto.Auth.AuthRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<AuthRequest> PARSER;

    public static com.google.protobuf.Parser<AuthRequest> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }

  public interface AuthResponseOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.nookure.staff.api.proto.AuthResponse)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>bool success = 1;</code>
     * @return The success.
     */
    boolean getSuccess();

    /**
     * <code>bool expired = 2;</code>
     * @return The expired.
     */
    boolean getExpired();

    /**
     * <code>string message = 3;</code>
     * @return The message.
     */
    java.lang.String getMessage();
    /**
     * <code>string message = 3;</code>
     * @return The bytes for message.
     */
    com.google.protobuf.ByteString
        getMessageBytes();

    /**
     * <code>optional string token = 4;</code>
     * @return Whether the token field is set.
     */
    boolean hasToken();
    /**
     * <code>optional string token = 4;</code>
     * @return The token.
     */
    java.lang.String getToken();
    /**
     * <code>optional string token = 4;</code>
     * @return The bytes for token.
     */
    com.google.protobuf.ByteString
        getTokenBytes();
  }
  /**
   * Protobuf type {@code com.nookure.staff.api.proto.AuthResponse}
   */
  public  static final class AuthResponse extends
      com.google.protobuf.GeneratedMessageLite<
          AuthResponse, AuthResponse.Builder> implements
      // @@protoc_insertion_point(message_implements:com.nookure.staff.api.proto.AuthResponse)
      AuthResponseOrBuilder {
    private AuthResponse() {
      message_ = "";
      token_ = "";
    }
    private int bitField0_;
    public static final int SUCCESS_FIELD_NUMBER = 1;
    private boolean success_;
    /**
     * <code>bool success = 1;</code>
     * @return The success.
     */
    @java.lang.Override
    public boolean getSuccess() {
      return success_;
    }
    /**
     * <code>bool success = 1;</code>
     * @param value The success to set.
     */
    private void setSuccess(boolean value) {
      
      success_ = value;
    }
    /**
     * <code>bool success = 1;</code>
     */
    private void clearSuccess() {

      success_ = false;
    }

    public static final int EXPIRED_FIELD_NUMBER = 2;
    private boolean expired_;
    /**
     * <code>bool expired = 2;</code>
     * @return The expired.
     */
    @java.lang.Override
    public boolean getExpired() {
      return expired_;
    }
    /**
     * <code>bool expired = 2;</code>
     * @param value The expired to set.
     */
    private void setExpired(boolean value) {
      
      expired_ = value;
    }
    /**
     * <code>bool expired = 2;</code>
     */
    private void clearExpired() {

      expired_ = false;
    }

    public static final int MESSAGE_FIELD_NUMBER = 3;
    private java.lang.String message_;
    /**
     * <code>string message = 3;</code>
     * @return The message.
     */
    @java.lang.Override
    public java.lang.String getMessage() {
      return message_;
    }
    /**
     * <code>string message = 3;</code>
     * @return The bytes for message.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getMessageBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(message_);
    }
    /**
     * <code>string message = 3;</code>
     * @param value The message to set.
     */
    private void setMessage(
        java.lang.String value) {
      java.lang.Class<?> valueClass = value.getClass();
  
      message_ = value;
    }
    /**
     * <code>string message = 3;</code>
     */
    private void clearMessage() {

      message_ = getDefaultInstance().getMessage();
    }
    /**
     * <code>string message = 3;</code>
     * @param value The bytes for message to set.
     */
    private void setMessageBytes(
        com.google.protobuf.ByteString value) {
      checkByteStringIsUtf8(value);
      message_ = value.toStringUtf8();

    }

    public static final int TOKEN_FIELD_NUMBER = 4;
    private java.lang.String token_;
    /**
     * <code>optional string token = 4;</code>
     * @return Whether the token field is set.
     */
    @java.lang.Override
    public boolean hasToken() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>optional string token = 4;</code>
     * @return The token.
     */
    @java.lang.Override
    public java.lang.String getToken() {
      return token_;
    }
    /**
     * <code>optional string token = 4;</code>
     * @return The bytes for token.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getTokenBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(token_);
    }
    /**
     * <code>optional string token = 4;</code>
     * @param value The token to set.
     */
    private void setToken(
        java.lang.String value) {
      java.lang.Class<?> valueClass = value.getClass();
  bitField0_ |= 0x00000001;
      token_ = value;
    }
    /**
     * <code>optional string token = 4;</code>
     */
    private void clearToken() {
      bitField0_ = (bitField0_ & ~0x00000001);
      token_ = getDefaultInstance().getToken();
    }
    /**
     * <code>optional string token = 4;</code>
     * @param value The bytes for token to set.
     */
    private void setTokenBytes(
        com.google.protobuf.ByteString value) {
      checkByteStringIsUtf8(value);
      token_ = value.toStringUtf8();
      bitField0_ |= 0x00000001;
    }

    public static com.nookure.staff.api.proto.Auth.AuthResponse parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static com.nookure.staff.api.proto.Auth.AuthResponse parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static com.nookure.staff.api.proto.Auth.AuthResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static com.nookure.staff.api.proto.Auth.AuthResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static com.nookure.staff.api.proto.Auth.AuthResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static com.nookure.staff.api.proto.Auth.AuthResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static com.nookure.staff.api.proto.Auth.AuthResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static com.nookure.staff.api.proto.Auth.AuthResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static com.nookure.staff.api.proto.Auth.AuthResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static com.nookure.staff.api.proto.Auth.AuthResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static com.nookure.staff.api.proto.Auth.AuthResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static com.nookure.staff.api.proto.Auth.AuthResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(com.nookure.staff.api.proto.Auth.AuthResponse prototype) {
      return DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code com.nookure.staff.api.proto.AuthResponse}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          com.nookure.staff.api.proto.Auth.AuthResponse, Builder> implements
        // @@protoc_insertion_point(builder_implements:com.nookure.staff.api.proto.AuthResponse)
        com.nookure.staff.api.proto.Auth.AuthResponseOrBuilder {
      // Construct using com.nookure.staff.api.proto.Auth.AuthResponse.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>bool success = 1;</code>
       * @return The success.
       */
      @java.lang.Override
      public boolean getSuccess() {
        return instance.getSuccess();
      }
      /**
       * <code>bool success = 1;</code>
       * @param value The success to set.
       * @return This builder for chaining.
       */
      public Builder setSuccess(boolean value) {
        copyOnWrite();
        instance.setSuccess(value);
        return this;
      }
      /**
       * <code>bool success = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearSuccess() {
        copyOnWrite();
        instance.clearSuccess();
        return this;
      }

      /**
       * <code>bool expired = 2;</code>
       * @return The expired.
       */
      @java.lang.Override
      public boolean getExpired() {
        return instance.getExpired();
      }
      /**
       * <code>bool expired = 2;</code>
       * @param value The expired to set.
       * @return This builder for chaining.
       */
      public Builder setExpired(boolean value) {
        copyOnWrite();
        instance.setExpired(value);
        return this;
      }
      /**
       * <code>bool expired = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearExpired() {
        copyOnWrite();
        instance.clearExpired();
        return this;
      }

      /**
       * <code>string message = 3;</code>
       * @return The message.
       */
      @java.lang.Override
      public java.lang.String getMessage() {
        return instance.getMessage();
      }
      /**
       * <code>string message = 3;</code>
       * @return The bytes for message.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getMessageBytes() {
        return instance.getMessageBytes();
      }
      /**
       * <code>string message = 3;</code>
       * @param value The message to set.
       * @return This builder for chaining.
       */
      public Builder setMessage(
          java.lang.String value) {
        copyOnWrite();
        instance.setMessage(value);
        return this;
      }
      /**
       * <code>string message = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearMessage() {
        copyOnWrite();
        instance.clearMessage();
        return this;
      }
      /**
       * <code>string message = 3;</code>
       * @param value The bytes for message to set.
       * @return This builder for chaining.
       */
      public Builder setMessageBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setMessageBytes(value);
        return this;
      }

      /**
       * <code>optional string token = 4;</code>
       * @return Whether the token field is set.
       */
      @java.lang.Override
      public boolean hasToken() {
        return instance.hasToken();
      }
      /**
       * <code>optional string token = 4;</code>
       * @return The token.
       */
      @java.lang.Override
      public java.lang.String getToken() {
        return instance.getToken();
      }
      /**
       * <code>optional string token = 4;</code>
       * @return The bytes for token.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getTokenBytes() {
        return instance.getTokenBytes();
      }
      /**
       * <code>optional string token = 4;</code>
       * @param value The token to set.
       * @return This builder for chaining.
       */
      public Builder setToken(
          java.lang.String value) {
        copyOnWrite();
        instance.setToken(value);
        return this;
      }
      /**
       * <code>optional string token = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearToken() {
        copyOnWrite();
        instance.clearToken();
        return this;
      }
      /**
       * <code>optional string token = 4;</code>
       * @param value The bytes for token to set.
       * @return This builder for chaining.
       */
      public Builder setTokenBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setTokenBytes(value);
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.nookure.staff.api.proto.AuthResponse)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new com.nookure.staff.api.proto.Auth.AuthResponse();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "bitField0_",
              "success_",
              "expired_",
              "message_",
              "token_",
            };
            java.lang.String info =
                "\u0000\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0007\u0002\u0007" +
                "\u0003\u0208\u0004\u1208\u0000";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<com.nookure.staff.api.proto.Auth.AuthResponse> parser = PARSER;
          if (parser == null) {
            synchronized (com.nookure.staff.api.proto.Auth.AuthResponse.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<com.nookure.staff.api.proto.Auth.AuthResponse>(
                        DEFAULT_INSTANCE);
                PARSER = parser;
              }
            }
          }
          return parser;
      }
      case GET_MEMOIZED_IS_INITIALIZED: {
        return (byte) 1;
      }
      case SET_MEMOIZED_IS_INITIALIZED: {
        return null;
      }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:com.nookure.staff.api.proto.AuthResponse)
    private static final com.nookure.staff.api.proto.Auth.AuthResponse DEFAULT_INSTANCE;
    static {
      AuthResponse defaultInstance = new AuthResponse();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        AuthResponse.class, defaultInstance);
    }

    public static com.nookure.staff.api.proto.Auth.AuthResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<AuthResponse> PARSER;

    public static com.google.protobuf.Parser<AuthResponse> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}