package gr.nifitsas.dealsapp.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {


  private String secretKey = "c6a73530fa62b3ed1cf77ea5c4ad42384647293ab60d447da4e784fd403703b8";
  private long jwtExpiration = 3600000;

  public String generateToken(String username, String role) {
    var claims = new HashMap<String, Object>();
    claims.put("role", role);
    return Jwts
      .builder()
      .setIssuer("self")
      .setClaims(claims)
      .setSubject(username)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String subject = extractSubject(token);
    return (subject.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  public String getStringClaim(String token, String claim) {
    return extractAllClaims(token).get(claim, String.class);
  }

  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(getSignInKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  /**
   * Creates a HS256 Key. Key is an interface.
   * Starting from secretKey we get a byte array
   * of the secret. Then we get the {@link javax.crypto.SecretKey,
   * class that implements the {@link Key } interface.
   *
   *
   * @return  a SecretKey which implements Key.
   */
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
