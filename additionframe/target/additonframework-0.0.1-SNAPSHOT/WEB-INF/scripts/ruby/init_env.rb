require 'java'
java_import 'com.genew.plat.ruby.RubyInitServletContextListener'
PROP_NAME = RubyInitServletContextListener::PROP_NAME_RUBY_GEM_HOME
ENV['GEM_HOME'] = Java::JavaLang::System.getProperty(PROP_NAME)

