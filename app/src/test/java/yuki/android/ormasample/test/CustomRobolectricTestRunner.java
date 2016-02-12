package yuki.android.ormasample.test;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

import yuki.android.ormasample.BuildConfig;

public class CustomRobolectricTestRunner extends RobolectricTestRunner {

    public CustomRobolectricTestRunner(Class<?> testClass)
            throws InitializationError {
        super(testClass);
        String buildVariant = (BuildConfig.FLAVOR.isEmpty()
                ? ""
                : BuildConfig.FLAVOR + "/") + BuildConfig.BUILD_TYPE;
        String intermediatesPath = BuildConfig.class.getResource("").toString()
                .replace("file:", "").replace("%20", " ");
        intermediatesPath = intermediatesPath
                .substring(0, intermediatesPath.indexOf("/classes"));

        System.setProperty("android.package", BuildConfig.APPLICATION_ID);
        System.setProperty("android.manifest",
                intermediatesPath + "/manifests/full/" + buildVariant
                        + "/AndroidManifest.xml");
        System.setProperty("android.resources",
                intermediatesPath + "/res/merged/" + buildVariant);
        System.setProperty("android.assets",
                intermediatesPath + "/assets/" + buildVariant);
    }
}