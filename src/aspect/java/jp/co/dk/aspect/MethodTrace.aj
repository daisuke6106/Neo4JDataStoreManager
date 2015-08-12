package jp.co.dk.aspect;

public aspect MethodTrace {
	
	protected jp.co.dk.logger.Logger logger = jp.co.dk.logger.LoggerFactory.getLogger(this.getClass());
	
	protected static int nestlevel = -1;
	
	pointcut methodTrace(): execution(* *.*(..)) && !execution(* *.toString()) && !execution(* *.hashCode()) ;
	
	before(): methodTrace() {
		nestlevel++;
		StringBuilder head = new StringBuilder();
		for (int i=0; i<nestlevel; i++)head.append("┃");
		org.aspectj.lang.Signature sig = thisJoinPoint.getSignature();
		
	    logger.trace(head.toString() + "┏METHOD[START]━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	    logger.trace(head.toString() + "┃class_name  :" + sig.getDeclaringType().getName());
	    logger.trace(head.toString() + "┃method_name :" + sig.getName());
	    if (thisJoinPoint.getArgs().length == 0) {
	    	logger.trace(head.toString() + "┃parametar  :nothing");
	    } else {
		    int index=1;
		    for (Object arg : thisJoinPoint.getArgs()) {
		    	if (arg != null) {
		    		logger.trace(head.toString() + "┃parametar [" + index + "]:" + arg.toString() + "(" + arg.getClass().getName() + ")");
		    	} else {
		    		logger.trace(head.toString() + "┃parametar [" + index + "]: is null");
		    	}
		    	index++;
		    }
	    }
	}
	
	after(): methodTrace() {
		// logger.trace("└METHOD[ FIN ]━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	}
	
	after() returning(Object o): methodTrace() {
		StringBuilder head = new StringBuilder();
		for (int i=0; i<nestlevel; i++)head.append("┃");
		
		org.aspectj.lang.Signature sig = thisJoinPoint.getSignature();
		logger.trace(head.toString() + "┃===Return Infomation===");
		if (o == null) {
			logger.trace(head.toString() + "┃return: is null");
		} else { 
			logger.trace(head.toString() + "┃return: " + o.toString());
		}
        logger.trace(head.toString() + "┗METHOD[ FIN ]━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        logger.trace(head.toString());
        nestlevel--;
    }

    after() throwing(Exception e): methodTrace() {
    	StringBuilder head = new StringBuilder();
		for (int i=0; i<nestlevel; i++)head.append("┃");
		
    	org.aspectj.lang.Signature sig = thisJoinPoint.getSignature();
    	logger.trace(head.toString() + "┃===Throw Infomation===");
        logger.trace(head.toString() + "┃throw: " + e.toString());
        logger.trace(head.toString() + "┗METHOD[ FIN ]━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        logger.trace(head.toString());
        nestlevel--;
    }

}