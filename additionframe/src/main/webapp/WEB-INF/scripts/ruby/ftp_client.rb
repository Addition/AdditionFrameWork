# encoding: utf-8
unless __FILE__ == $0
  require 'java'
  java_import 'com.genew.plat.ruby.RubyInitServletContextListener'
  PROP_NAME = RubyInitServletContextListener::PROP_NAME_RUBY_SCRIPTS_PATH
  SCRIPTS_PATH = Java::JavaLang::System.getProperty(PROP_NAME)

  require SCRIPTS_PATH + '/init_env'
  require SCRIPTS_PATH + '/java_object'
  java_import 'com.genew.nuas.api.service.IFtpClient'
end
require 'net/ftp'
require 'fileutils'

module Net
  class FTP
    def mkdir_p(dir)
      dir.split('/')[1..-1].each do |e|
        mkdir e if (nlst(e).empty? rescue true)
        chdir e
      end
    end
  end
end

class FtpClientRubyImpl
  unless __FILE__ == $0
    include IFtpClient
  else
    def initialize(ip=nil, port=21)
      @ip, @port = ip, port
    end
  end

  def setNuServicesConfig(config)
    @ip = config.ftp_server_ip
    @port = config.ftp_server_port
  end

  def download(user, pwd, r2l_hash )
    begin
      client = signin user, pwd
      r2l_hash.each_pair do |k, v|
        client.chdir '/'
        raise "#{k} not found" if (client.nlst(k).empty? rescue true)
        dirname = File.dirname v
        FileUtils.mkdir_p dirname unless File.directory? dirname
        client.getbinaryfile k, v
      end
      client.quit
    ensure
      client.close
    end
  end

  def upload(user, pwd, r2l_hash)
    begin
      client = signin user, pwd
      r2l_hash.each_pair do |k, v|
        client.chdir '/'
        client.mkdir_p File.dirname(k)
        client.putbinaryfile v, File.basename(k)
      end
      client.quit
    ensure
      client.close
    end
  end

  private
  def signin(user, pwd)
    puts "try to signin to ftp server, ip=#{@ip}, port=#{@port}, user=#{user}, pwd=***"
    client = Net::FTP.new
    client.connect @ip, @port
    client.login user, pwd
    client
  end
end

if __FILE__ == $0
  client = FtpClientRubyImpl.new '10.8.9.151', 21
  client.upload 'weixuanan01', '888888', '/2222adddd/34-23434234-3424/ade-234-349234ab.zip' => 'D:/workspace/coder/my-scripts/niu/tests/files/big.zip'
  #client.download 'weixuanan01', '888888', '/2222adddd/34-23434234-3424/ade-234-349234ab.zip' => 'D:/abc/big.zip'
else
  FtpClientRubyImpl.new
end

