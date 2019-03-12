package osgi;

import java.io.File;
import java.util.Collection;
import java.util.function.Consumer;

import com.dscsag.plm.spi.interfaces.ECTRService;
import com.dscsag.plm.spi.interfaces.objects.ObjectData;
import com.dscsag.plm.spi.interfaces.process.PluginProcessContainer;
import com.dscsag.plm.spi.interfaces.process.extensions.ApplCreateDocumentsConstants;
import com.dscsag.plm.spi.interfaces.process.extensions.ApplCreateDocumentsExtensionService;
import com.dscsag.plm.spi.interfaces.process.extensions.ApplFileDocument;

/**
 * Import on Demand handler which determines own filenames for the new created documents
 *
 */
public class ImportOnDemandHandler implements ApplCreateDocumentsExtensionService
{
  private ECTRService ectrService;

  public ImportOnDemandHandler(ECTRService ectrService)
  {
    this.ectrService=ectrService;
  }

  @Override
  public Consumer<PluginProcessContainer> beforeUpdateSession()
  {
    return procContainer -> {
      Collection<ApplFileDocument> fileDocs = procContainer.getParameter(DATAKEY_FILE_DOCUMENTS);

      ectrService.getPlmLogger().trace(this.getClass().getName()+": documents to process: "+fileDocs.size());
      fileDocs.forEach(fileDoc -> {
        ObjectData objDataForDoc = fileDoc.getDocumentData();
        String newFileName = objDataForDoc.objectData().get(ApplCreateDocumentsConstants.APPL_CREATE_DOC_NEW_FILE);
        File newFile = new File(newFileName);
        File path = newFile.getParentFile();
        String filename = newFile.getName();
        String changedFilename = filename;
        int idxOfDot = filename.lastIndexOf('.');
        if(idxOfDot>0)
        {
          String filenameOnly = filename.substring(0,idxOfDot);
          String extension = filename.substring(idxOfDot);
          changedFilename = filenameOnly + "-changed-by-osgi"+extension;
        }
        if(!changedFilename.equals(filename))
        {
          File changedFile = new File(path,changedFilename);
          changedFilename = changedFile.getAbsolutePath();
          objDataForDoc.objectData().put(ApplCreateDocumentsConstants.APPL_CREATE_DOC_NEW_FILE,changedFilename);
          ectrService.getPlmLogger().trace(this.getClass().getName()+": changed filename: " +newFileName + " -> " +changedFilename);
        }
      });
    };
  }

}