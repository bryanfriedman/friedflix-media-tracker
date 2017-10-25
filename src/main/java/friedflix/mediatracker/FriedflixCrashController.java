package friedflix.mediatracker;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crash")
public class FriedflixCrashController {
	
	@Autowired
    private ApplicationContext appContext;	
	
	@RequestMapping(method = RequestMethod.GET)
	int crash() {
		return SpringApplication.exit(appContext);
	}
    
}
