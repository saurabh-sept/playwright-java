# About
This is a test automation framework utilising open source technologies, namely, `Playwright`, `Java`, `Maven` with `TestNG` . It can do both UI and API based test automation.

It currently has UI tests automated for demo puposes in the `tests` folder.

# System requirements
Windows 10+, Windows Server 2016+ or Windows Subsystem for Linux (WSL).

MacOS 12 Monterey or MacOS 13 Ventura.

Debian 11, Debian 12, Ubuntu 20.04 or Ubuntu 22.04, with x86-64 or arm64 architecture.

JDK1.8+

# Installation
One very convenient way to install is through a package manager, example, `Chocolatey` `https://chocolatey.org/install`.

VS Code: `choco install vscode.install`. Install `VS Code extension` for Playwright.

Git: `choco install git`

`Clone` the repo from github.

Each version of Playwright needs specific versions of browser binaries to operate. You will need to use the Playwright CLI to install these browsers. With every release, Playwright updates the versions of the browsers it supports, so that the latest Playwright would support the latest browsers at any moment. It means that every time you update Playwright, you might need to re-run the install CLI command.

# Running tests and reporting
## From VSCode Test Explorer: 
Run or Debug tests 

## From Command line:
For full suite: `mvn clean test`

For individual files: `mvn clean test -Dsuite-xml="test-suites/loginTestSuite.xml"`

For Playwright tracing log: `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace target/trace.zip"`

For Surefire testng reports: `target/surefire-reports/`

Tests are currently only configured to run in `Chromium` browser, which can be modified

# Video
It's `off` at the moment but can be turned.
