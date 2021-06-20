package cz.partners.checker.antivir;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fi.solita.clamav.ClamAVClient;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClamAvAntivir implements Antivir {

    @Value("${partners.clamAV.hostname}")
    private String hostname;

    @Value("${partners.clamAV.port}")
    private int port;

    public boolean check(byte[] content) throws CanNotCheckException {
        ClamAVClient clamAVClient = new ClamAVClient(hostname, port);
        byte[] reply;
        try {
            reply = clamAVClient.scan(content);
        } catch (Exception e) {
            throw new CanNotCheckException(e);
        }
        if (!ClamAVClient.isCleanReply(reply)) {
            log.error("Scanning failed");
            return false;
        }
        return true;
    }

}

