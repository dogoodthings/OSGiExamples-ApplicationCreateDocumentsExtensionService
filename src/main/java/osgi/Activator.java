package osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.dscsag.plm.spi.interfaces.ECTRService;
import com.dscsag.plm.spi.interfaces.process.extensions.ApplCreateDocumentsExtensionService;

/**
 * Activator to register provided services
 */
public class Activator implements BundleActivator
{

  @Override
  public void start(BundleContext context) throws Exception
  {
    // first, get ECTRService which provides some common useful services like
    // logging,
    // dictionary and so on.
    ECTRService ectrService = getService(context, ECTRService.class);

    // then register our service, which can influence the import on demand regarding file names
    context.registerService(ApplCreateDocumentsExtensionService.class, new ImportOnDemandHandler(ectrService), null);
  }

  @Override
  public void stop(BundleContext context) throws Exception
  {
    // empty
  }

  private <T> T getService(BundleContext context, Class<T> clazz) throws Exception
  {
    ServiceReference<T> serviceRef = context.getServiceReference(clazz);
    if (serviceRef != null)
      return context.getService(serviceRef);
    throw new Exception("Unable to find implementation for service " + clazz.getName());
  }

}