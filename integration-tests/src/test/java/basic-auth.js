function(credentials) {
    var tmp = credentials.user + ':' + credentials.password;
    var Base64 = Java.type('java.util.Base64');
    var encoded = Base64.getEncoder().encodeToString(tmp.bytes);
    return 'Basic ' + encoded;
}