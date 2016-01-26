package jp.co.dk.aspect;

public aspect MethodTrace {
	
	protected java.util.Stack<Long> timeStack = new java.util.Stack<Long>();
	
	protected jp.co.dk.logger.Logger logger = jp.co.dk.logger.LoggerFactory.getLogger(this.getClass());
	
	protected int nestlevel = -1;
	
	pointcut methodTrace(): execution(* *.*(..)) && !execution(* *.toString()) && !execution(* *.hashCode()) ;
	
	before(): methodTrace() {
		nestlevel++;
		StringBuilder head = new StringBuilder();
		for (int i=0; i<nestlevel; i++)head.append("|       ");
		org.aspectj.lang.Signature sig = thisJoinPoint.getSignature();
		
		StringBuilder traceStr = new StringBuilder(head);
		traceStr.append("[START] ");
		traceStr.append(sig.getDeclaringType().getName()).append('#').append(sig.getName());
		
	    if (thisJoinPoint.getArgs().length == 0) {
	    	traceStr.append("()");
	    } else {
	    	traceStr.append("(");
	    	for (Object arg : thisJoinPoint.getArgs()) {
		    	if (arg != null) {
		    		traceStr.append(arg.toString() + "(" + arg.getClass().getName() + ")");
		    	} else {
		    		traceStr.append("null");
		    	}
		    	traceStr.append(",");
		    }
		    traceStr.append(")");
	    }
	    logger.trace(traceStr.toString());
	    
	    // ログ出力処理を開始後、メソッドの開始時刻を保存する。
	    timeStack.add(new Long(new java.util.Date().getTime()));
	}
	
	after(): methodTrace() {
		// logger.trace("└METHOD[ FIN ]━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	}
	
	after() returning(Object o): methodTrace() {
		
		StringBuilder head = new StringBuilder();
		for (int i=0; i<nestlevel; i++)head.append("|       ");
		
		org.aspectj.lang.Signature sig = thisJoinPoint.getSignature();
		
		StringBuilder traceStr = new StringBuilder(head);
		traceStr.append("[ FIN ] ");
		traceStr.append(sig.getDeclaringType().getName()).append('#').append(sig.getName());
		traceStr.append(" - SUCCESS( ").append(new java.util.Date().getTime() - timeStack.pop().longValue()).append(" msec )");
		
		if (o == null) {
			traceStr.append(" return: null");
		} else { 
			traceStr.append(" return: " );
			traceStr.append(o.toString());
			traceStr.append("(");
			traceStr.append(o.getClass().getName());
			traceStr.append(")");
		}
		logger.trace(traceStr.toString());
        nestlevel--;
    }

    after() throwing(Exception e): methodTrace() {
    	
    	StringBuilder head = new StringBuilder();
		for (int i=0; i<nestlevel; i++)head.append("|       ");
		
		org.aspectj.lang.Signature sig = thisJoinPoint.getSignature();
		
        StringBuilder traceStr = new StringBuilder(head);
        traceStr.append("[ FIN ] ");
        traceStr.append(sig.getDeclaringType().getName()).append('#').append(sig.getName());
		traceStr.append(" - ERROR( ").append(new java.util.Date().getTime() - timeStack.pop().longValue()).append(" msec )");
		
		if (e == null) {
			traceStr.append(" throw: null");
		} else { 
			traceStr.append(" throw: " );
			traceStr.append(e.toString());
			traceStr.append("(");
			traceStr.append(e.getClass().getName());
			traceStr.append(")");
		}
		logger.trace(traceStr.toString());
        nestlevel--;
    }

}