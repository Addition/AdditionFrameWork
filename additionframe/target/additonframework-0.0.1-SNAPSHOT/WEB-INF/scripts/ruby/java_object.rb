# encoding: utf-8
module JavaObject
  def attr_accessor_in_java_style(*attrs)
    attr_reader_in_java_style *attrs
    attr_writer_in_java_style *attrs
  end

  def attr_reader_in_java_style(*attrs)
    attrs.each do |e|
      c = "get#{camelize(e.to_s)}"
      define_method(c) do
        instance_variable_get("@#{e}")
      end
    end
  end

  def attr_writer_in_java_style(*attrs)
    attrs.each do |e|
      c = "set#{camelize(e.to_s)}"
      define_method(c) do |value|
        instance_variable_set("@#{e}", value)
      end
    end
  end

  def camelize(str)
    tmp = str.split('_').map { |e| e.capitalize }.join
  end
end
