# Android Project Template

## はじめに

Androidプロジェクトで頻繁に使用されるプラグインの導入ソースプログラムをプロジェクト作成の都度書き直すのは非効率的である.  
そういった繰り返し書かれるプログラムをこのプロジェクトにまとめ, 新規プロジェクト作成の際にはこれをコピーすることで対応できるようにする.  

### 導入されるプラグイン

主要な静的解析ツールと各種ユーティリティが導入される.  
プラグインの導入ソースコード(gralde)は`/android.gradle`で定義されている.  
これに含まれるプラグインは次の通り.

FindBugs  
: Javaプログラム(バイトコード)の静的解析ツール.  

PMD  
: Javaプログラム(ソースコード)の静的解析ツール.  

CheckStyle  
: プログラムの体裁チェックツール.  

Lint  
: Androidに特化した潜在的な不具合を検出する静的解析ツール.  

Jacoco
: Javaプログラムのカバレッジレポート.  

DexCount  
: APKが持つメソッド数を報告するプラグイン.  

ApkSize  
: APKのバイナリサイズを報告するプラグイン.  

GradleVersion  
: 依存しているライブラリの最新バージョンをチェックするプラグイン.  


### ライブラリ

 - RxAndroid
 - RxJava
 - Dagger2
 - ButterKnife
 - Orma
 - Timber
 - Stetho
 - Okhttp
 - Robolectric
 - JUnit
 - Mockito
 - Hamcrest
 - Android support Appcompat-v7
 - Android support annotations
 - Android design support lib.
 - Android support testing lib.


## android.gradleの適用

`android.gradle`をアプリケーションに適用するには2ステップ必要.  
まずプロジェクトルートで下記を宣言する.  

```gradle
buildscript {
    dependencies {
        ...

        // dex method count
        classpath 'com.getkeepsafe.dexcount:dexcount-gradle-plugin:0.4.1'

        // apk size
        classpath 'com.vanniktech:gradle-android-apk-size-plugin:0.2.0'

        // check for plugin updates
        classpath 'com.github.ben-manes:gradle-versions-plugin:0.11.3'
    }
}
```

次に, 各モジュールの`build.gradle`で`android.gradle`プラグインを読み込む.  

```gradle
apply from: rootProject.file('android.gradle')
```

### 追加・変更されるタスク

findbugs{variantName}  
: FindBugsによる静的解析を指定のビルドバリアントに対して実行する  

pmd{variantName}  
: PMDによる静的解析を指定のビルドバリアントに対して実行する  

checkstyle{variantName}  
: CheckStyleによる体裁チェックを指定のビルドバリアントに対して実行する  

lint{variantName}  
: Lintによる静的解析を指定のビルドバリアントに対して実行する  

jacoco{variantName}Report  
: Jacocoによるカバレッジレポートを指定のビルドバリアントに対して実行する  

count{variantName}Methods  
: DexCountによるメソッド数の計測を指定のビルドバリアントに対して実行する  

size{variantName}  
: ApkSizeによるAPKサイズの計測を指定のビルドバリアントに対して実行する  

dependencyUpdates  
: 依存しているライブラリの最新バージョンチェックを実行する  

check  
: デバッガブルなビルドタイプの場合, いくつかの静的解析チェックを追加で行う  

pullCodeStyleSettings  
: AndroidStudioに適用されるコードスタイル設定ファイルをダウンロードする  

checkEnvironmentSettings  
: 開発環境の設定確認用タスク  

## コンフィギュレーション

### CheckStyle

各静的解析ツールはデフォルトで`/config`に格納されたコンフィギュレーションファイルを読み込む.  
CheckStyleのコンフィギュレーションファイルは2種類用意されている.

`checkstyle-easy.xml`は緩い体裁チェックルール. `checkstyle-hard.xml`は厳しい体裁チェックルールとなっている.
プロジェクトのコーディング規約にあったファイルを`android.gradle`で指定する.

```gradle
/*
 * CheckStyle
 *   see: https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.Checkstyle.html
 */
task("checkstyle$variantName", type: Checkstyle,
        dependsOn: "assemble$variantName") {
...
    configFile = rootProject.file('config/checkstyle-easy.xml')
...
}
```

### Release署名

APKのDebug/Release署名設定も`android.gradle`で定義されている.  
Release署名で使用されるキーストア情報は`/secret`に格納されている`release.gradle`にある(`secret`フォルダについては後述).  
`android.gradle`は`/secret/release.gradle`を参照し, これを適用する.    
もし`release.gradle`が見つからない場合はDebug署名の内容がRelease署名として流用される.  

```gradle
def releaseSettingGradleFile = rootProject.file('secret/release.gradle')
if (releaseSettingGradleFile.exists()) {
    apply from: releaseSettingGradleFile, to: android
} else {
    println "\n\t!! NOT FOUND RELEASE KEYSTORE SETTING. SIGNING DEBUG KEYSTORE !!\n"
    release {
        storeFile = debug.storeFile
        storePassword = debug.storePassword
        keyAlias = debug.keyAlias
        keyPassword = debug.keyPassword
    }
}
```

Debug署名はIDE標準で用意される`debug.keystore`をプロジェクトルートに配置することで利用できる.  

### コードスタイル設定

AndroidStudioで使用するコードスタイル設定が`/.idea/codeStyleSettings.xml`に定義されている.  
`android.gradle`の`pullCodeStyleSettings`タスクを実行することで下記のシェルスクリプトが実行され, AndroidStudioのコードスタイル設定が更新される.

```bash
curl -L "https://raw.githubusercontent.com/YukiMatsumura/AndroidProjectTemplate/master/.idea/codeStyleSettings.xml" > .idea/codeStyleSettings.xml
```

コードスタイルを適用するにはIDEを再起動すること.  

## Checkタスク

FindBugs, PMD, CheckStyle, Jacocoはビルドバリアント毎に定義されたタスクを持つ.  
例) findbugsDevDebug  
`android.gradle`ではDebuggableなビルドタイプに限定してこれらのタスクをCheckタスクに依存させている.  
(ビルドタイプの限定を解除する場合はCIサービスでメモリ使用量が増えるため事前に確認が必要)  

```gradle
if (variant.buildType.debuggable) {
    check.dependsOn "pmd${variantName}"
    check.dependsOn "findbugs${variantName}"
    check.dependsOn "checkstyle${variantName}"
    check.dependsOn "jacoco${variantName}Report"
}
```

## その他

`android.gradle`で定義される`checkEnvironmentSettings`タスクはプロジェクトに必要な環境をチェックするためのタスク.  
標準でJDKのバージョンチェックを実施する.  

```gradle
task checkEnvironmentSettings() {
    group 'Verification'
    description "Check environment settings"

    // Ormaはaptによるコード生成にJava1.8を要求する
    if (JavaVersion.current() < JavaVersion.VERSION_1_8) {
        println("\n\tYou will need Java 1.8 or higher if you use Orma.")
        println("\tCurrent Java version is really old. Found ver. " + JavaVersion.current() + ".\n")
        throw new GradleException("Please Update your Java.")
    }
}
```
