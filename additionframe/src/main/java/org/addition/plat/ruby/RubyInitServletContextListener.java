package org.addition.plat.ruby;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RubyInitServletContextListener implements ServletContextListener
{
    private static final Logger LOGGER =
            LoggerFactory.getLogger(RubyInitServletContextListener.class);

    public static final String PROP_NAME_RUBY_SCRIPTS_PATH = "ruby.scripts.path";

    public static final String PROP_NAME_RUBY_GEM_HOME = "ruby.gem.home";

    public static final String CONTEXT_PARAM_NAME_RUBY_SCRIPTS_PATH = PROP_NAME_RUBY_SCRIPTS_PATH;

    public static final String CONTEXT_PARAM_NAME_RUBY_GEM_HOME = PROP_NAME_RUBY_GEM_HOME;

    public void contextInitialized(ServletContextEvent sce)
    {
        LOGGER.info("context initializing...");
        initRubyGemHome(sce);
        initRubyScriptsPath(sce);
    }

    private void initRubyGemHome(ServletContextEvent sce)
    {
        final String sysPropRubyGemHome = System.getProperty(PROP_NAME_RUBY_GEM_HOME);
        if (null != sysPropRubyGemHome)
        {
            LOGGER.info("retrieve {} from system property, {}={}", new Object[]
            {
                PROP_NAME_RUBY_GEM_HOME,
                PROP_NAME_RUBY_GEM_HOME,
                sysPropRubyGemHome
            });
            return;
        }
        final ServletContext servletContext = sce.getServletContext();
        final String appPath = servletContext.getRealPath("/");
        String initParameterRubyGemHome =
                servletContext.getInitParameter(CONTEXT_PARAM_NAME_RUBY_GEM_HOME);
        if (null != initParameterRubyGemHome)
        {
            initParameterRubyGemHome = appPath + initParameterRubyGemHome;
            LOGGER.info("retrieve {} from web context parameter, {}={}", new Object[]
            {
                PROP_NAME_RUBY_GEM_HOME,
                PROP_NAME_RUBY_GEM_HOME,
                initParameterRubyGemHome
            });
            System.setProperty(PROP_NAME_RUBY_GEM_HOME, initParameterRubyGemHome);
            return;
        }
        final String rubyGemHome = appPath + "/WEB-INF/lib/ruby-gems";
        LOGGER.info("retrieve {} by convention, {}={}", new Object[]
        {
            PROP_NAME_RUBY_GEM_HOME,
            PROP_NAME_RUBY_GEM_HOME,
            rubyGemHome
        });
        System.setProperty(PROP_NAME_RUBY_GEM_HOME, rubyGemHome);
    }

    private void initRubyScriptsPath(ServletContextEvent sce)
    {
        final String sysPropRubyScriptsPath = System.getProperty(PROP_NAME_RUBY_SCRIPTS_PATH);
        if (null != sysPropRubyScriptsPath)
        {
            LOGGER.info("retrieve {} from system property, {}={}", new Object[]
            {
                PROP_NAME_RUBY_SCRIPTS_PATH,
                PROP_NAME_RUBY_SCRIPTS_PATH,
                sysPropRubyScriptsPath
            });
            return;
        }
        final ServletContext servletContext = sce.getServletContext();
        final String appPath = servletContext.getRealPath("/");
        String initParameterRubyScriptsPath =
                servletContext.getInitParameter(CONTEXT_PARAM_NAME_RUBY_SCRIPTS_PATH);
        if (null != initParameterRubyScriptsPath)
        {
            initParameterRubyScriptsPath = appPath + initParameterRubyScriptsPath;
            LOGGER.info("retrieve {} from web context parameter, {}={}", new Object[]
            {
                PROP_NAME_RUBY_SCRIPTS_PATH,
                PROP_NAME_RUBY_SCRIPTS_PATH,
                initParameterRubyScriptsPath
            });
            System.setProperty(PROP_NAME_RUBY_SCRIPTS_PATH, initParameterRubyScriptsPath);
            return;
        }
        final String rubyScriptsPath = appPath + "/WEB-INF/scripts/ruby";
        LOGGER.info("retrieve {} by convention, {}={}", new Object[]
        {
            PROP_NAME_RUBY_SCRIPTS_PATH,
            PROP_NAME_RUBY_SCRIPTS_PATH,
            rubyScriptsPath
        });
        System.setProperty(PROP_NAME_RUBY_SCRIPTS_PATH, rubyScriptsPath);
    }

    public void contextDestroyed(ServletContextEvent sce)
    {
        LOGGER.info("context destroying...");
    }
}
