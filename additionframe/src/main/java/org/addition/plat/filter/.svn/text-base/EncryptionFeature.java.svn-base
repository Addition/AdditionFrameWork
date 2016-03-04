package org.addition.plat.filter;

import org.addition.plat.encrypt.EncryptionService;
import org.apache.cxf.Bus;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("encryptionFeature")
public class EncryptionFeature extends AbstractFeature
{
    private static final DecryptionInInterceptor IN = new DecryptionInInterceptor();

    private static final EncryptionOutInterceptor OUT = new EncryptionOutInterceptor();

    private EncryptionService encryptionService;

    public EncryptionService getEncryptionService()
    {
        return encryptionService;
    }

    @Autowired(required = true)
    public void setEncryptionService(EncryptionService encryptionService)
    {
        this.encryptionService = encryptionService;
    }

    @Override
    protected void initializeProvider(InterceptorProvider provider, Bus bus)
    {
        IN.setEncryptionService(getEncryptionService());
        OUT.setEncryptionService(getEncryptionService());
        provider.getInInterceptors().add(IN);
        provider.getOutInterceptors().add(OUT);
        provider.getOutFaultInterceptors().add(OUT);
    }
}
