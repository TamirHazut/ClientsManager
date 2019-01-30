package General;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public abstract class FilesCopier {
	
	public static void copyFile(String dest, String originFile) throws IOException {
		File file = new File(dest);
		if (!file.exists()) {
			file.mkdirs();
		}
		Path from = Paths.get(originFile);
		Path to = Paths.get(dest);
		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES };
		Files.copy(from, to, options);
	}
		
}
