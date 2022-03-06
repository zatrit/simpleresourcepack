package net.zatrit.srp;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.YamlRepresenter;
import org.json.simple.JSONObject;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.*;
import java.net.URL;
import java.util.LinkedHashMap;

import static net.zatrit.srp.SimpleResourcePack.LOGGER;
import static net.zatrit.srp.Utils.MESSAGE_DIGEST;
import static net.zatrit.srp.Utils.checksum;

public class ConfigData implements Serializable {
    public String url = "";
    public String hash = "";

    private static final Yaml yml;

    public static ConfigData fromFile(File f) throws IOException {
        ConfigData data = null;

        if (f.exists()) {
            FileInputStream is = new FileInputStream(f);
            Gson gson=new Gson();
            var map = yml.loadAs(is, LinkedHashMap.class);
            var json = new JSONObject(map);
            data = gson.fromJson(json.toJSONString(), ConfigData.class);

            is.close();
        }

        if (data != null)
            return data;

        data = new ConfigData();
        data.save(f);

        return data;
    }

    public void save(File f) {
        try {
            save(f, yml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save(File f, Yaml yml) throws IOException {
        f.getParentFile().mkdir();
        FileWriter writer = new FileWriter(f);
        yml.dump(this, writer);
        writer.close();
    }

    static {
        var yamlRepresent = new YamlRepresenter();
        yamlRepresent.addClassTag(ConfigData.class, Tag.MAP);
        yamlRepresent.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        yml = new Yaml(yamlRepresent);
    }

    public void generateHash() {
        if (!StringUtils.isEmpty(url)) {
            try {
                var webStream = new URL(url).openStream();
                hash = checksum(MESSAGE_DIGEST, webStream).trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            LOGGER.warning("Resource pack URL is NULL, couldn't generate hash");
        }
    }
}
