package com.ovo.fintech.proxydroid.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by aenriko on 17/01/18.
 */

public class IdUtil {
  public static String generateTransactionId() {
    String uuid = UUID.randomUUID().toString().replace("-", "");
    String date = new SimpleDateFormat("MMddHHmmssSS").format(new Date());
    return date + uuid.substring(0,11);
  }
  public static String generateRequestId() {
    String uuid = UUID.randomUUID().toString().replace("-", "");
    String date = new SimpleDateFormat("MMddHHmmssSS").format(new Date());
    return date + uuid.substring(0,11);
  }
}
