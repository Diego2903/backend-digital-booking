package com.digital.api.digital_booking.services;

import com.digital.api.digital_booking.dto.PageResponseDTO;
import com.digital.api.digital_booking.dto.SignUpRequest;
import com.digital.api.digital_booking.dto.UserDTO;
import com.digital.api.digital_booking.jwt.JwtService;
import com.digital.api.digital_booking.models.PageResponse;
import com.digital.api.digital_booking.models.Role;
import com.digital.api.digital_booking.models.Users;
import com.digital.api.digital_booking.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService{

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConversionService conversionService;
    private final EmailService emailService;
    private final JwtService jwtService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByEmail(email);
        if (userDetails == null) {
            throw new UsernameNotFoundException(email);
        }
        return userDetails;
    }


    public UserDetails createUser(SignUpRequest signUpRequest) throws ErrorResponseException {

    //nombre si tiene espacio en blanco poner _ en vez de espacio
        String name = signUpRequest.getName().replace(" ", "_");
        if (userRepository.findByEmail(signUpRequest.getEmail()) != null ) {
            //encontrar el usuario por el email
            Users user = userRepository.findByEmail(signUpRequest.getEmail());
            //si el usuario no esta activo
            if (user.getEnabled()){
                System.out.println("El usuario ya existe");
                //error 500
                throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                System.out.println("El usuario ya existe pero no esta activo");
                String htmlContent = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                        "  <title>Confirmación de Correo </title>\n" +
                        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                        "  <style type=\"text/css\">\n" +
                        "  /**\n" +
                        "   * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\n" +
                        "   */\n" +
                        "  @media screen {\n" +
                        "    @font-face {\n" +
                        "      font-family: 'Source Sans Pro';\n" +
                        "      font-style: normal;\n" +
                        "      font-weight: 400;\n" +
                        "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n" +
                        "    }\n" +
                        "    @font-face {\n" +
                        "      font-family: 'Source Sans Pro';\n" +
                        "      font-style: normal;\n" +
                        "      font-weight: 700;\n" +
                        "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n" +
                        "    }\n" +
                        "  }\n" +
                        "  /**\n" +
                        "   * Avoid browser level font resizing.\n" +
                        "   * 1. Windows Mobile\n" +
                        "   * 2. iOS / OSX\n" +
                        "   */\n" +
                        "  body,\n" +
                        "  table,\n" +
                        "  td,\n" +
                        "  a {\n" +
                        "    -ms-text-size-adjust: 100%; /* 1 */\n" +
                        "    -webkit-text-size-adjust: 100%; /* 2 */\n" +
                        "  }\n" +
                        "  /**\n" +
                        "   * Remove extra space added to tables and cells in Outlook.\n" +
                        "   */\n" +
                        "  table,\n" +
                        "  td {\n" +
                        "    mso-table-rspace: 0pt;\n" +
                        "    mso-table-lspace: 0pt;\n" +
                        "  }\n" +
                        "  /**\n" +
                        "   * Better fluid images in Internet Explorer.\n" +
                        "   */\n" +
                        "  img {\n" +
                        "    -ms-interpolation-mode: bicubic;\n" +
                        "  }\n" +
                        "  /**\n" +
                        "   * Remove blue links for iOS devices.\n" +
                        "   */\n" +
                        "  a[x-apple-data-detectors] {\n" +
                        "    font-family: inherit !important;\n" +
                        "    font-size: inherit !important;\n" +
                        "    font-weight: inherit !important;\n" +
                        "    line-height: inherit !important;\n" +
                        "    color: inherit !important;\n" +
                        "    text-decoration: none !important;\n" +
                        "  }\n" +
                        "  /**\n" +
                        "   * Fix centering issues in Android 4.4.\n" +
                        "   */\n" +
                        "  div[style*=\"margin: 16px 0;\"] {\n" +
                        "    margin: 0 !important;\n" +
                        "  }\n" +
                        "  body {\n" +
                        "    width: 100% !important;\n" +
                        "    height: 100% !important;\n" +
                        "    padding: 0 !important;\n" +
                        "    margin: 0 !important;\n" +
                        "  }\n" +
                        "  /**\n" +
                        "   * Collapse table borders to avoid space between cells.\n" +
                        "   */\n" +
                        "  table {\n" +
                        "    border-collapse: collapse !important;\n" +
                        "  }\n" +
                        "  a {\n" +
                        "    color: #1a82e2;\n" +
                        "  }\n" +
                        "  img {\n" +
                        "    height: auto;\n" +
                        "    line-height: 100%;\n" +
                        "    text-decoration: none;\n" +
                        "    border: 0;\n" +
                        "    outline: none;\n" +
                        "  }\n" +
                        "  </style>\n" +
                        "\n" +
                        "</head>\n" +
                        "<body style=\"background-color: #fff;\">\n" +
                        "\n" +
                        "  <!-- start preheader -->\n" +
                        "  <div\n" +
                        "    class=\"preheader\"\n" +
                        "    style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #ffffff; opacity: 0;\">\n" +
                        "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n" +
                        "  </div>\n" +
                        "  <!-- end preheader -->\n" +
                        "\n" +
                        "  <!-- start body -->\n" +
                        "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                        "\n" +
                        "    <!-- start logo -->\n" +
                        "    <tr>\n" +
                        "      <td align=\"center\" bgcolor=\"#fff\">\n" +
                        "        <!--[if (gte mso 9)|(IE)]>\n" +
                        "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                        "        <tr>\n" +
                        "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                        "        <![endif]-->\n" +
                        "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                        "          <tr>\n" +
                        "            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n" +
                        "              <a href=\"https://example.com\" target=\"_blank\" style=\"display: inline-block;\">\n" +
                        "                <img\n" +
                        "                  src=\"https://s3.amazonaws.com/s3buckets.c3.g5/logo.jpg\"\n" +
                        "                  alt=\"Logo\"\n" +
                        "                  border=\"0\"\n" +
                        "                  style=\"display: block; width: 200px; max-width: 200px; min-width: 48px;\">\n" +
                        "              </a>\n" +
                        "            </td>\n" +
                        "          </tr>\n" +
                        "        </table>\n" +
                        "        <!--[if (gte mso 9)|(IE)]>\n" +
                        "        </td>\n" +
                        "        </tr>\n" +
                        "        </table>\n" +
                        "        <![endif]-->\n" +
                        "      </td>\n" +
                        "    </tr>\n" +
                        "    <!-- end logo -->\n" +
                        "\n" +
                        "    <!-- start hero -->\n" +
                        "    <tr>\n" +
                        "      <td align=\"center\" bgcolor=\"#fff\">\n" +
                        "        <!--[if (gte mso 9)|(IE)]>\n" +
                        "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                        "        <tr>\n" +
                        "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                        "        <![endif]-->\n" +
                        "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                        "          <tr>\n" +
                        "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', sans-serif; border-top: 3px solid #d4dadf;\">\n" +
                        "              <h1\n" +
                        "                style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px; color: #000000;\">\n" +
                        "                Confirma tu dirección de correo electrónico\n" +
                        "              </h1>\n" +
                        "            </td>\n" +
                        "          </tr>\n" +
                        "        </table>\n" +
                        "        <!--[if (gte mso 9)|(IE)]>\n" +
                        "        </td>\n" +
                        "        </tr>\n" +
                        "        </table>\n" +
                        "        <![endif]-->\n" +
                        "      </td>\n" +
                        "    </tr>\n" +
                        "    <!-- end hero -->\n" +
                        "\n" +
                        "    <!-- start copy block -->\n" +
                        "    <tr>\n" +
                        "      <td align=\"center\" bgcolor=\"#fff\">\n" +
                        "        <!--[if (gte mso 9)|(IE)]>\n" +
                        "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                        "        <tr>\n" +
                        "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                        "        <![endif]-->\n" +
                        "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                        "\n" +
                        "          <!-- start copy -->\n" +
                        "          <tr>\n" +
                        "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                        "              <p style=\"margin: 0;\">\n" +
                        "                Hola,\n" +  signUpRequest.getName().toUpperCase() +
                        "                <br>\n" +
                        "                Gracias por registrarse. Haga clic en el botón a continuación para confirmar su dirección de correo electrónico.\n" +
                        "              </p>\n" +
                        "            </td>\n" +
                        "          </tr>\n" +
                        "          <!-- end copy -->\n" +
                        "\n" +
                        "          <!-- start button -->\n" +
                        "          <tr>\n" +
                        "            <td align=\"left\" bgcolor=\"#ffffff\">\n" +
                        "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                        "                <tr>\n" +
                        "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">\n" +
                        "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                        "                      <tr>\n" +
                        "                        <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">\n" +
                        "                          <a\n" +
                        "                            href=\"http://127.0.0.1:5173/user/" + name + "\"\n" +
                        "                            target=\"_blank\"\n" +
                        "                            style=\"display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;\"\n" +
                        "                          >Confirme su Correo</a\n" +
                        "                          >\n" +
                        "                        </td>\n" +
                        "                      </tr>\n" +
                        "                    </table>\n" +
                        "                  </td>\n" +
                        "                </tr>\n" +
                        "              </table>\n" +
                        "            </td>\n" +
                        "          </tr>\n" +
                        "          <!-- end button -->\n" +
                        "\n" +
                        "          <!-- start copy -->\n" +
                        "          <tr>\n" +
                        "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                        "              <p style=\"margin: 0;\">\n" +
                        "                Si no se registró en nuestro sitio web, ignore este correo electrónico.\n" +
                        "              </p>\n" +
                        "            </td>\n" +
                        "          </tr>\n" +
                        "          <!-- end copy -->\n" +
                        "\n" +
                        "        </table>\n" +
                        "        <!--[if (gte mso 9)|(IE)]>\n" +
                        "        </td>\n" +
                        "        </tr>\n" +
                        "        </table>\n" +
                        "        <![endif]-->\n" +
                        "      </td>\n" +
                        "    </tr>\n" +
                        "    <!-- end copy block -->\n" +
                        "\n" +
                        "    <!-- start footer -->\n" +
                        "    <tr>\n" +
                        "      <td align=\"center\" bgcolor=\"#fff\" style=\"padding: 24px;\">\n" +
                        "        <!--[if (gte mso 9)|(IE)]>\n" +
                        "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                        "        <tr>\n" +
                        "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                        "        <![endif]-->\n" +
                        "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                        "\n" +
                        "          <!-- start unsubscribe -->\n" +
                        "          <tr>\n" +
                        "            <td align=\"center\" bgcolor=\"#fff\" style=\"padding: 12px 24px;\">\n" +
                        "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                        "                <tr>\n" +
                        "                  <td align=\"center\" bgcolor=\"#fff\" style=\"padding: 12px 24px;\">\n" +
                        "                    <p\n" +
                        "                      style=\"margin: 0; font-family: 'Source Sans Pro', sans-serif; font-size: 14px; line-height: 20px; color: #666666;\">\n" +
                        "                      You received this email because you signed up for our website. If you no longer wish to receive\n" +
                        "                      emails from us, you can\n" +
                        "                      <a href=\"#\" target=\"_blank\" style=\"color: #666666; font-weight: bold;\">unsubscribe</a>\n" +
                        "                      at any time.\n" +
                        "                    </p>\n" +
                        "                  </td>\n" +
                        "                </tr>\n" +
                        "              </table>\n" +
                        "            </td>\n" +
                        "          </tr>\n" +
                        "          <!-- end unsubscribe -->\n" +
                        "\n" +
                        "        </table>\n" +
                        "        <!--[if (gte mso 9)|(IE)]>\n" +
                        "        </td>\n" +
                        "        </tr>\n" +
                        "        </table>\n" +
                        "        <![endif]-->\n" +
                        "      </td>\n" +
                        "    </tr>\n" +
                        "    <!-- end footer -->\n" +
                        "\n" +
                        "  </table>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>";


                emailService.sendMail(signUpRequest.getEmail(),
                            "Confirma tu correo electronico",
                            htmlContent);
                    //actualizar fecha de creacion
                    user.setCreatedAt(LocalDateTime.now());

                    userRepository.save(user);

                return userRepository.findByEmail(signUpRequest.getEmail());
            }
    }else{
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\n" +
                    "  <meta charset=\"utf-8\">\n" +
                    "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                    "  <title>Confirmación de Correo </title>\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "  <style type=\"text/css\">\n" +
                    "  /**\n" +
                    "   * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\n" +
                    "   */\n" +
                    "  @media screen {\n" +
                    "    @font-face {\n" +
                    "      font-family: 'Source Sans Pro';\n" +
                    "      font-style: normal;\n" +
                    "      font-weight: 400;\n" +
                    "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n" +
                    "    }\n" +
                    "    @font-face {\n" +
                    "      font-family: 'Source Sans Pro';\n" +
                    "      font-style: normal;\n" +
                    "      font-weight: 700;\n" +
                    "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n" +
                    "    }\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Avoid browser level font resizing.\n" +
                    "   * 1. Windows Mobile\n" +
                    "   * 2. iOS / OSX\n" +
                    "   */\n" +
                    "  body,\n" +
                    "  table,\n" +
                    "  td,\n" +
                    "  a {\n" +
                    "    -ms-text-size-adjust: 100%; /* 1 */\n" +
                    "    -webkit-text-size-adjust: 100%; /* 2 */\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Remove extra space added to tables and cells in Outlook.\n" +
                    "   */\n" +
                    "  table,\n" +
                    "  td {\n" +
                    "    mso-table-rspace: 0pt;\n" +
                    "    mso-table-lspace: 0pt;\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Better fluid images in Internet Explorer.\n" +
                    "   */\n" +
                    "  img {\n" +
                    "    -ms-interpolation-mode: bicubic;\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Remove blue links for iOS devices.\n" +
                    "   */\n" +
                    "  a[x-apple-data-detectors] {\n" +
                    "    font-family: inherit !important;\n" +
                    "    font-size: inherit !important;\n" +
                    "    font-weight: inherit !important;\n" +
                    "    line-height: inherit !important;\n" +
                    "    color: inherit !important;\n" +
                    "    text-decoration: none !important;\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Fix centering issues in Android 4.4.\n" +
                    "   */\n" +
                    "  div[style*=\"margin: 16px 0;\"] {\n" +
                    "    margin: 0 !important;\n" +
                    "  }\n" +
                    "  body {\n" +
                    "    width: 100% !important;\n" +
                    "    height: 100% !important;\n" +
                    "    padding: 0 !important;\n" +
                    "    margin: 0 !important;\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Collapse table borders to avoid space between cells.\n" +
                    "   */\n" +
                    "  table {\n" +
                    "    border-collapse: collapse !important;\n" +
                    "  }\n" +
                    "  a {\n" +
                    "    color: #1a82e2;\n" +
                    "  }\n" +
                    "  img {\n" +
                    "    height: auto;\n" +
                    "    line-height: 100%;\n" +
                    "    text-decoration: none;\n" +
                    "    border: 0;\n" +
                    "    outline: none;\n" +
                    "  }\n" +
                    "  </style>\n" +
                    "\n" +
                    "</head>\n" +
                    "<body style=\"background-color: #fff;\">\n" +
                    "\n" +
                    "  <!-- start preheader -->\n" +
                    "  <div\n" +
                    "    class=\"preheader\"\n" +
                    "    style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #ffffff; opacity: 0;\">\n" +
                    "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n" +
                    "  </div>\n" +
                    "  <!-- end preheader -->\n" +
                    "\n" +
                    "  <!-- start body -->\n" +
                    "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "\n" +
                    "    <!-- start logo -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#fff\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n" +
                    "              <a href=\"https://example.com\" target=\"_blank\" style=\"display: inline-block;\">\n" +
                    "                <img\n" +
                    "                  src=\"https://s3.amazonaws.com/s3buckets.c3.g5/logo.jpg\"\n" +
                    "                  alt=\"Logo\"\n" +
                    "                  border=\"0\"\n" +
                    "                  style=\"display: block; width: 200px; max-width: 200px; min-width: 48px;\">\n" +
                    "              </a>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end logo -->\n" +
                    "\n" +
                    "    <!-- start hero -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#fff\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', sans-serif; border-top: 3px solid #d4dadf;\">\n" +
                    "              <h1\n" +
                    "                style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px; color: #000000;\">\n" +
                    "                Confirma tu dirección de correo electrónico.\n" +
                    "              </h1>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end hero -->\n" +
                    "\n" +
                    "    <!-- start copy block -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#fff\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "\n" +
                    "          <!-- start copy -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                    "              <p style=\"margin: 0;\">\n" +
                    "                Hola,\n" +  signUpRequest.getName().toUpperCase() +
                    "                <br>\n" +
                    "                Gracias por registrarse. Haga clic en el botón a continuación para confirmar su dirección de correo electrónico.\n" +
                    "              </p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end copy -->\n" +
                    "\n" +
                    "          <!-- start button -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\">\n" +
                    "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                <tr>\n" +
                    "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">\n" +
                    "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                      <tr>\n" +
                    "                        <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">\n" +
                    "                          <a\n" +
                    "                            href=\"http://127.0.0.1:5173/user/" + name + "\"\n" +
                    "                            target=\"_blank\"\n" +
                    "                            style=\"display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;\"\n" +
                    "                          >Confirme su Correo</a\n" +
                    "                          >\n" +
                    "                        </td>\n" +
                    "                      </tr>\n" +
                    "                    </table>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </table>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end button -->\n" +
                    "\n" +
                    "          <!-- start copy -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                    "              <p style=\"margin: 0;\">\n" +
                    "                Si no se registró en nuestro sitio web, ignore este correo electrónico.\n" +
                    "              </p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end copy -->\n" +
                    "\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end copy block -->\n" +
                    "\n" +
                    "    <!-- start footer -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#fff\" style=\"padding: 24px;\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "\n" +
                    "          <!-- start unsubscribe -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" bgcolor=\"#fff\" style=\"padding: 12px 24px;\">\n" +
                    "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                <tr>\n" +
                    "                  <td align=\"center\" bgcolor=\"#fff\" style=\"padding: 12px 24px;\">\n" +
                    "                    <p\n" +
                    "                      style=\"margin: 0; font-family: 'Source Sans Pro', sans-serif; font-size: 14px; line-height: 20px; color: #666666;\">\n" +
                    "                      You received this email because you signed up for our website. If you no longer wish to receive\n" +
                    "                      emails from us, you can\n" +
                    "                      <a href=\"#\" target=\"_blank\" style=\"color: #666666; font-weight: bold;\">unsubscribe</a>\n" +
                    "                      at any time.\n" +
                    "                    </p>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </table>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end unsubscribe -->\n" +
                    "\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end footer -->\n" +
                    "\n" +
                    "  </table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";



            emailService.sendMail(signUpRequest.getEmail(),
                    "Confirma tu correo electronico",
                    htmlContent);
            return userRepository.save(Users.builder()
                    .email(signUpRequest.getEmail())
                    .name(signUpRequest.getName())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .role(Role.USER)
                    .createdAt(LocalDateTime.now( )) //fecha actual
                    .build());

        }


    }

    @Override
    public PageResponse<UserDTO> getUsers(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Users> users = userRepository.findAll(pageRequest);

        return new PageResponse<UserDTO>(
                users.getTotalElements(),
                users.getTotalPages(),
                users.getNumber(),
                users.getSize(),
                conversionService.convert(users.getContent(), List.class)

        );






    }

    @Override
    public Users getUser(String jwt) {
        if (jwt != null) {
            String email = jwtService.extractEmail(jwt);
            System.out.println("email : " + email);
            Users user = userRepository.findByEmail(email);
            System.out.println("user : " + user);
            System.out.println(conversionService.convert(user, UserDTO.class));
             return user;

        }
return null;
    }

    @Override
       public Users updateUser(String email, Users user) {
        Users userToUpdate = userRepository.findByEmail(email);
        System.out.println("userToUpdate : " + userToUpdate);


        if (userToUpdate != null) {
            if (user.getName() != null) {
                userToUpdate.setName(user.getName());
            }
            if (user.getEmail() != null) {
                userToUpdate.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                userToUpdate.setPassword(user.getPassword());
            }
            if (user.getRole() != null) {
                userToUpdate.setRole(user.getRole());
            }
            //enabled
            if (user.getEnabled()) {
                userToUpdate.setEnabled(user.getEnabled());
            }
            return userRepository.save(userToUpdate);

        }
        return null;
    }

    @Override
    public Users updateUserState(String email) throws Exception {
        // quitar _ de email por espacio en blanco
        String name = email.replace("_", " ");
        Users userToUpdate = userRepository.getByName(name);
        System.out.println("name: " + name + " userToUpdate: " + userToUpdate);
        System.out.println("userToUpdate: " + userToUpdate);

        // si la fecha de creacion es mayor a 48 horas no se puede activar
        if (userToUpdate.getCreatedAt().isBefore(LocalDateTime.now().minusHours(48))) {
            System.out.println("userToUpdate: " + userToUpdate + " no actualizado");
            throw new Exception("El usuario no puede ser actualizado.");
        }

        try {
            System.out.println("userToUpdate: " + userToUpdate + " actualizado");
            userToUpdate.setEnabled(true);
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\n" +
                    "  <meta charset=\"utf-8\">\n" +
                    "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                    "  <title>Confirmación de Correo </title>\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "  <style type=\"text/css\">\n" +
                    "  /**\n" +
                    "   * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\n" +
                    "   */\n" +
                    "  @media screen {\n" +
                    "    @font-face {\n" +
                    "      font-family: 'Source Sans Pro';\n" +
                    "      font-style: normal;\n" +
                    "      font-weight: 400;\n" +
                    "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n" +
                    "    }\n" +
                    "    @font-face {\n" +
                    "      font-family: 'Source Sans Pro';\n" +
                    "      font-style: normal;\n" +
                    "      font-weight: 700;\n" +
                    "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n" +
                    "    }\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Avoid browser level font resizing.\n" +
                    "   * 1. Windows Mobile\n" +
                    "   * 2. iOS / OSX\n" +
                    "   */\n" +
                    "  body,\n" +
                    "  table,\n" +
                    "  td,\n" +
                    "  a {\n" +
                    "    -ms-text-size-adjust: 100%; /* 1 */\n" +
                    "    -webkit-text-size-adjust: 100%; /* 2 */\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Remove extra space added to tables and cells in Outlook.\n" +
                    "   */\n" +
                    "  table,\n" +
                    "  td {\n" +
                    "    mso-table-rspace: 0pt;\n" +
                    "    mso-table-lspace: 0pt;\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Better fluid images in Internet Explorer.\n" +
                    "   */\n" +
                    "  img {\n" +
                    "    -ms-interpolation-mode: bicubic;\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Remove blue links for iOS devices.\n" +
                    "   */\n" +
                    "  a[x-apple-data-detectors] {\n" +
                    "    font-family: inherit !important;\n" +
                    "    font-size: inherit !important;\n" +
                    "    font-weight: inherit !important;\n" +
                    "    line-height: inherit !important;\n" +
                    "    color: inherit !important;\n" +
                    "    text-decoration: none !important;\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Fix centering issues in Android 4.4.\n" +
                    "   */\n" +
                    "  div[style*=\"margin: 16px 0;\"] {\n" +
                    "    margin: 0 !important;\n" +
                    "  }\n" +
                    "  body {\n" +
                    "    width: 100% !important;\n" +
                    "    height: 100% !important;\n" +
                    "    padding: 0 !important;\n" +
                    "    margin: 0 !important;\n" +
                    "  }\n" +
                    "  /**\n" +
                    "   * Collapse table borders to avoid space between cells.\n" +
                    "   */\n" +
                    "  table {\n" +
                    "    border-collapse: collapse !important;\n" +
                    "  }\n" +
                    "  a {\n" +
                    "    color: #1a82e2;\n" +
                    "  }\n" +
                    "  img {\n" +
                    "    height: auto;\n" +
                    "    line-height: 100%;\n" +
                    "    text-decoration: none;\n" +
                    "    border: 0;\n" +
                    "    outline: none;\n" +
                    "  }\n" +
                    "  </style>\n" +
                    "\n" +
                    "</head>\n" +
                    "<body style=\"background-color: #fff;\">\n" +
                    "\n" +
                    "  <!-- start preheader -->\n" +
                    "  <div\n" +
                    "    class=\"preheader\"\n" +
                    "    style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #ffffff; opacity: 0;\">\n" +
                    "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n" +
                    "  </div>\n" +
                    "  <!-- end preheader -->\n" +
                    "\n" +
                    "  <!-- start body -->\n" +
                    "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "\n" +
                    "    <!-- start logo -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#fff\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n" +
                    "              <a href=\"https://example.com\" target=\"_blank\" style=\"display: inline-block;\">\n" +
                    "                <img\n" +
                    "                  src=\"https://s3.amazonaws.com/s3buckets.c3.g5/logo.jpg\"\n" +
                    "                  alt=\"Logo\"\n" +
                    "                  border=\"0\"\n" +
                    "                  style=\"display: block; width: 200px; max-width: 200px; min-width: 48px;\">\n" +
                    "              </a>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end logo -->\n" +
                    "\n" +
                    "    <!-- start hero -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#fff\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', sans-serif; border-top: 3px solid #d4dadf;\">\n" +
                    "              <h1\n" +
                    "                style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px; color: #000000;\">\n" +
                    "                Registro Exitoso\n" +
                    "              </h1>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end hero -->\n" +
                    "\n" +
                    "    <!-- start copy block -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#fff\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "\n" +
                    "          <!-- start copy -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                    "              <p style=\"margin: 0;\">\n" +
                    "                Hola,\n" +  userToUpdate.getName().toUpperCase() +
                    "                <br>\n" +
                    "                Gracias por registrarse. Su registro fue exitoso.\n" +
                    "              </p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end copy -->\n" +
                    "\n" +
                    "          <!-- start button -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\">\n" +
                    "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                <tr>\n" +
                    "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">\n" +
                    "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                      <tr>\n" +
                    "                        <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">\n" +
                    "                        </td>\n" +
                    "                      </tr>\n" +
                    "                    </table>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </table>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end button -->\n" +
                    "\n" +
                    "          <!-- start copy -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                    "              <p style=\"margin: 0;\">\n" +
                    "                Si no se registró en nuestro sitio web, ignore este correo electrónico.\n" +
                    "              </p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end copy -->\n" +
                    "\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end copy block -->\n" +
                    "\n" +
                    "    <!-- start footer -->\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\" bgcolor=\"#fff\" style=\"padding: 24px;\">\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                    "        <tr>\n" +
                    "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                    "        <![endif]-->\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                    "\n" +
                    "          <!-- start unsubscribe -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" bgcolor=\"#fff\" style=\"padding: 12px 24px;\">\n" +
                    "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                <tr>\n" +
                    "                  <td align=\"center\" bgcolor=\"#fff\" style=\"padding: 12px 24px;\">\n" +
                    "                    <p\n" +
                    "                      style=\"margin: 0; font-family: 'Source Sans Pro', sans-serif; font-size: 14px; line-height: 20px; color: #666666;\">\n" +
                    "                      You received this email because you signed up for our website. If you no longer wish to receive\n" +
                    "                      emails from us, you can\n" +
                    "                      <a href=\"#\" target=\"_blank\" style=\"color: #666666; font-weight: bold;\">unsubscribe</a>\n" +
                    "                      at any time.\n" +
                    "                    </p>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </table>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          <!-- end unsubscribe -->\n" +
                    "\n" +
                    "        </table>\n" +
                    "        <!--[if (gte mso 9)|(IE)]>\n" +
                    "        </td>\n" +
                    "        </tr>\n" +
                    "        </table>\n" +
                    "        <![endif]-->\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <!-- end footer -->\n" +
                    "\n" +
                    "  </table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";


            emailService.sendMail(userToUpdate.getEmail(),
                    "Registro de Exitoso",
                    htmlContent);

            return userRepository.save(userToUpdate);
        } catch (Exception e) {
            throw new Exception("Error al actualizar el estado del usuario.");
        }
    }



}
