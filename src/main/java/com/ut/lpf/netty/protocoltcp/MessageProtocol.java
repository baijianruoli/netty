package com.ut.lpf.netty.protocoltcp;

import lombok.Data;

@Data
public class MessageProtocol {
         private int len;
         private byte[] content;
}
