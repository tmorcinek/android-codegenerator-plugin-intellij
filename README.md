[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-android--codegenerator--plugin--intellij-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1443)

Android Studio/IntelliJ IDEA Plugin for Android code generation
================

Plugin for generation of Android code from XML files (layouts, menus).

For more information please see [the website](http://tmorcinek.github.io/android-codegenerator-plugin-intellij)

Or see the plugin in [jetbrains repository](https://plugins.jetbrains.com/plugin/7595?pr=idea).


Core Library
-------

This plugin is based on a library: [android-codegenerator-library](https://github.com/tmorcinek/android-codegenerator-library).
The library is responsible for:

 - extracting important information from XML files
 - generating Java code based on extracted information and templates


JetBrains Repository
-------

You can install plugin from repository: 
`Preferences/Settings->Plugins->Browse repositories...` 
Then type in search: 
`Android code Generator`


Download & Installation
-------

All available versions of plugin are in [releases](https://github.com/tmorcinek/android-codegenerator-plugin-intellij/releases)
You can download zip file from [latest release](https://github.com/tmorcinek/android-codegenerator-plugin-intellij/releases/latest) and install it manually in:  
`Preferences/Settings->Plugins->Install plugin from disk` 


Change notes
-------

<b>Version 1.6</b>
<ul>
    <li>ISSUE #11: Add Butter Knife support.
</ul>

<b>Version 1.5</b>
<ul>
    <li>ISSUE #19: Code generation dialog box is out of the screen!
    <li>ISSUE #13: Change Plugin name
</ul>

<b>Version 1.4</b>
<ul>
    <li>ISSUE #7: Android Studio throwing error when Android Designer editor is open.</li>
    <li>ISSUE #8: Source path's on windows are too long.</li>
    <li>ISSUE #9: Error when creating file on windows .</li>
    <li>ISSUE #10: Adding github.io page link to Plugin description.</li>
</ul>

<b>Version 1.3</b>
<ul>
    <li>Support for IntelliJ IDEA 14.</li>
</ul>

<b>Version 1.2</b>
<ul>
    <li>ISSUE #2: Templates should be editable in PreferencesPages.</li>
    <li>ISSUE #6: When plugin is about to override existing file content user should be informed.</li>
    <li>ISSUE #5: Source path should be retrieved from project and there should be combo instead of text field.</li>
</ul>

<b>Version 1.1</b>
<ul>
    <li>ISSUE #1: Editor should pops up when creating file.</li>
    <li>ISSUE #3: Source path should be saved in persistence store.</li>
    <li>ISSUE #4: Generate Code Menu Options should be available through editor.</li>
</ul>

<b>Version 1.0</b>
<ul>
    <li>generating Activity Class code from layout</li>
    <li>generating Fragment Class code from layout</li>
    <li>generating Adapter Class code from item layout</li>
    <li>generating Menu code from menu xml</li>
    <li>creating files from code</li>
    <li>preview screen with generated code that can be modified before creation of file</li>
    <li>detection of project package</li>
</ul>


License
-------

    Copyright 2014 Tomasz Morcinek.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
