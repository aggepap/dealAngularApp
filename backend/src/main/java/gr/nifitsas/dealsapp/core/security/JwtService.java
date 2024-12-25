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

  /**
   * Generates a JWT token for a user with the given username and role.
   *
   * @param username The username of the user.
   * @param role The user's role.
   * @return A JWT token string.
   */
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

  /**
   * Validates a JWT token.
   *
   * @param token The JWT token string.
   * @param userDetails The UserDetails object for the user.
   * @return True if the token is valid, false otherwise.
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String subject = extractSubject(token);
    return (subject.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }
  /**
   * Extracts a string claim from a JWT token.
   *
   * @param token The JWT token string.
   * @param claim The claim name to extract.
   * @return The value of the claim as a String, or null if the claim is not present.
   */
  public String getStringClaim(String token, String claim) {
    return extractAllClaims(token).get(claim, String.class);
  }
  /**
   * Extracts the subject (username) from a JWT token.
   *
   * @param token The JWT token string.
   * @return The username from the subject claim.
   */
  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a claim from a JWT token using a claim resolver function.
   *
   * @param token The JWT token string.
   * @param claimsResolver A function that extracts the claim value from the Claims object.
   * @param <T> The type of the claim value.
   * @return The extracted claim value.
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }
  /**
   * Checks if a JWT token is expired.
   *
   * @param token The JWT token string.
   * @return True if the token is expired, false otherwise.
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
  /**
   * Extracts the expiration date from a JWT token.
   *
   * @param token The JWT token string.
   * @return The expiration date of the token.
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Parses the JWT token claims.
   *
   * @param token The JWT token string.
   * @return The parsed Claims object.
   */
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
