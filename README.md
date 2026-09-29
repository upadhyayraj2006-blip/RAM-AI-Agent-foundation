# RAM-AI-Agent-foundation
You are my senior Android AI-agent engineer.

IMPORTANT:
I am NOT building a UGC reel generator.
I want to build an Android AI Agent application similar in concept to the Android AI assistant demonstrated in the reference video I provided.

The goal is to create my own Android AI assistant that can understand voice commands, understand the current Android screen, perform permitted Android UI actions, search the web, and assist with coding and development tasks.

PROJECT NAME:
RAM AI Agent

CORE IDEA:

User speaks:
"Open Chrome and search for the latest AI news."

The system should:

Voice Input
    ↓
Speech-to-Text
    ↓
AI Agent
    ↓
Understand Intent
    ↓
Select Tool
    ↓
Android/Web/Screen/Code Tool
    ↓
Perform Action
    ↓
Observe Result
    ↓
AI verifies result
    ↓
Voice/Text response

IMPORTANT ENGINEERING RULES:

1. First inspect the existing repository and README.md.
2. Do not delete useful existing files.
3. Do not blindly rewrite the repository.
4. Build the project incrementally.
5. After each major implementation, compile/test the project.
6. Fix compilation errors before moving to the next feature.
7. Never hard-code API keys.
8. Use environment/local configuration for secrets.
9. Clearly separate AI reasoning from Android actions.
10. Dangerous or irreversible actions must require user confirmation.
11. Do not bypass Android security restrictions.
12. Do not use root access.
13. Do not use hidden or unauthorized access to other apps.
14. The user must explicitly enable required Android permissions/services.
15. If a requested feature is impossible because of Android restrictions, implement the closest legitimate alternative and explain it.

TARGET PLATFORM:

Android application.

Prefer:
- Kotlin
- Modern Android SDK
- Jetpack Compose where appropriate
- Coroutines
- Android AccessibilityService for permitted UI automation
- Android speech recognition / microphone APIs
- Android notification system
- Foreground service only where appropriate and permitted
- WebView/Chrome intents where appropriate
- HTTP client for web/API requests
- Clean modular architecture

MAIN FEATURES:

==================================================
FEATURE 1 — AI CHAT ASSISTANT
==================================================

Create a main assistant screen containing:

- Chat messages
- Text input
- Microphone button
- Send button
- Agent status
- Tool/action status

Example:

User:
"Hello Maria"

Assistant:
"Hello! How can I help?"

The assistant should support natural language commands.

==================================================
FEATURE 2 — VOICE COMMANDS
==================================================

Add microphone functionality.

Flow:

Microphone
→ Speech recognition
→ User command
→ AI Agent
→ Action
→ Response

Examples:

"Open Chrome."

"Go home."

"Scroll down."

"Go back."

"Take a screenshot."

"What's on my screen?"

"Search the web for today's AI news."

"Open YouTube."

The app must clearly indicate when microphone recognition is active.

Do not continuously record audio without explicit user activation.

==================================================
FEATURE 3 — ANDROID ACCESSIBILITY AGENT
==================================================

Create an AccessibilityService.

The service should support permitted operations such as:

- Read accessibility/UI nodes where available
- Identify visible text
- Identify clickable elements
- Perform clicks
- Perform gestures
- Swipe
- Scroll
- Back
- Home
- Recent apps
- Open notifications where permitted
- Interact with supported UI elements

Create an abstraction:

AndroidActionTool

Possible actions:

CLICK
LONG_CLICK
SWIPE
SCROLL
BACK
HOME
RECENTS
TYPE_TEXT
READ_SCREEN

Do not execute arbitrary actions without validation.

The user must explicitly enable the Accessibility Service in Android Settings.

==================================================
FEATURE 4 — SCREEN UNDERSTANDING
==================================================

Implement screen observation.

The agent should be able to obtain an Android screenshot through legitimate Android APIs where available.

Create:

ScreenUnderstandingTool

Input:
Current screen

Output:

- Visible text
- Important UI elements
- Possible buttons
- Current app/package if available
- Short natural-language description

Example:

User:
"What is on my screen?"

Agent:
"You are viewing a YouTube video. The video player is visible at the top and the description panel is open."

If visual AI is unavailable, fall back to accessibility/UI-node information.

==================================================
FEATURE 5 — WEB SEARCH
==================================================

Create:

WebSearchTool

The agent should be able to search the web through an appropriate web/API mechanism.

Example:

User:
"Search for the latest AI news."

Agent:
Searches web
→ receives results
→ summarizes results
→ answers user.

Do not pretend to have searched if the search tool failed.

==================================================
FEATURE 6 — TOOL-CALLING AI AGENT
==================================================

Create a central AgentEngine.

The AgentEngine should decide which tool is required.

Example:

User:
"Open Chrome."

AgentEngine:
→ AndroidActionTool

User:
"What's on my screen?"

AgentEngine:
→ ScreenUnderstandingTool

User:
"Search latest Android news."

AgentEngine:
→ WebSearchTool

User:
"Create a website for a bakery."

AgentEngine:
→ CodingTool

The AI must NOT directly execute arbitrary shell commands.

It should select from registered, validated tools.

==================================================
FEATURE 7 — CODING AGENT
==================================================

Create a basic CodingTool.

The user should be able to ask:

"Create a professional bakery website."

The agent should:

1. Understand the request.
2. Create project files inside a controlled workspace.
3. Generate HTML/CSS/JavaScript or another supported project.
4. Inspect generated files.
5. Run safe development/build commands.
6. Detect build errors.
7. Attempt fixes.
8. Re-run validation.
9. Provide a local preview.

The coding workspace must be sandboxed.

Never expose arbitrary host-system access to the AI.

==================================================
FEATURE 8 — AUTO ERROR FIXING
==================================================

When a coding/build error occurs:

Error
→ AI analyzes error
→ identifies relevant file
→ proposes patch
→ applies safe patch
→ runs validation again

Maximum automatic retry:
3 attempts.

After 3 failed attempts, stop and explain the error instead of looping forever.

==================================================
FEATURE 9 — LOCAL PROJECT PREVIEW
==================================================

Create a development preview feature.

For generated web projects:

Project
→ development server
→ local preview URL
→ open preview in Android WebView/browser.

Do not expose the development server publicly by default.

==================================================
FEATURE 10 — AGENT MEMORY
==================================================

Create basic local conversation history.

Store:

- User message
- Assistant response
- Tool used
- Tool result summary
- Timestamp

Do not store microphone recordings by default.

Provide a clear way to clear conversation history.

==================================================
FEATURE 11 — AGENT TOOLS
==================================================

Create a clean tool interface.

Example conceptual structure:

Tool
- name
- description
- input schema
- execute()
- result

Implement initial tools:

1. AndroidActionTool
2. ScreenUnderstandingTool
3. WebSearchTool
4. CodingTool
5. FileTool
6. BrowserTool

Only expose safe, validated actions.

==================================================
FEATURE 12 — AGENT LOOP
==================================================

Implement:

User Request
↓
AI Planning
↓
Tool Selection
↓
Tool Execution
↓
Observe Result
↓
AI Verification
↓
Continue / Finish
↓
Response

Example:

User:
"Open Chrome and search for Android AI news."

Agent:

1. Understand command.
2. Open Chrome.
3. Wait for Chrome.
4. Verify screen.
5. Find/search field.
6. Enter search query.
7. Submit.
8. Observe result.
9. Summarize.

If a step fails, the agent should re-observe the screen and retry safely.

Do NOT create infinite loops.

==================================================
FEATURE 13 — USER CONFIRMATION
==================================================

For actions with meaningful consequences, require confirmation.

Examples:

- Sending messages
- Deleting files
- Making purchases
- Changing important settings
- Installing software
- Publishing content
- Sharing personal information

Example:

Assistant:
"I am ready to delete this file. Do you want me to continue?"

Buttons:

CONFIRM
CANCEL

==================================================
FEATURE 14 — ALWAYS-AVAILABLE ASSISTANT
==================================================

Design the architecture so a future version can support an assistant accessible from outside the main app.

However:

Do not violate Android background-execution restrictions.

Start with a reliable foreground/in-app assistant.

Then implement background functionality only where Android officially permits it.

==================================================
FEATURE 15 — UI
==================================================

Create a modern dark AI-assistant interface.

Main screen:

--------------------------------
RAM AI
--------------------------------

Agent Status:
● Ready

Conversation

User:
Open Chrome.

AI:
Opening Chrome...

Tool:
AndroidActionTool
✓ Completed

--------------------------------

[ 🎤 ] Type a command...

--------------------------------

Include:

- Agent status
- Tool execution status
- Voice button
- Stop button
- Settings
- Permission setup
- Conversation history

==================================================
FEATURE 16 — SETTINGS
==================================================

Create settings for:

- AI provider/API configuration
- Voice input
- Voice output
- Accessibility Service status
- Screen understanding
- Web search
- Agent confirmation mode
- Conversation history
- Debug mode

Never display API secrets in plain text.

==================================================
FEATURE 17 — PERMISSION SETUP
==================================================

Create a setup/onboarding screen explaining required permissions.

Examples:

Microphone
Accessibility Service
Notifications if required
Screen capture if required

The app must explain WHY each permission is needed.

Do not silently request permissions.

==================================================
FEATURE 18 — AI PROVIDER
==================================================

Create an AI provider abstraction.

Example:

AIProvider

Methods:

generateResponse()
planToolCall()
analyzeScreen()
analyzeError()

Make the implementation replaceable.

Use Gemini-compatible configuration through environment/build configuration where appropriate.

Never hard-code API keys.

If no AI API key is available, create a MOCK/DEMO mode so the project can still compile and demonstrate the UI/tool architecture.

==================================================
FEATURE 19 — DEBUGGING
==================================================

Add structured logs for:

Agent request
Tool selection
Tool execution
Tool result
Errors
Retry count

Never log passwords, API keys, private messages, or sensitive personal data.

==================================================
FEATURE 20 — TESTING
==================================================

Create tests for:

- Agent command parsing
- Tool selection
- Android action validation
- Web search tool
- Coding tool
- Error handling
- Retry limit
- Confirmation flow

==================================================
DEVELOPMENT STRATEGY
==================================================

DO NOT attempt to build every feature at once.

Build in phases.

PHASE 1:
Create the Android application skeleton.

PHASE 2:
Create the chat UI.

PHASE 3:
Create voice input.

PHASE 4:
Create AccessibilityService and permission setup.

PHASE 5:
Create basic Android actions:
HOME
BACK
RECENTS
CLICK
SWIPE
SCROLL

PHASE 6:
Create screen understanding.

PHASE 7:
Create WebSearchTool.

PHASE 8:
Create AI AgentEngine and tool calling.

PHASE 9:
Create CodingTool.

PHASE 10:
Create local preview and auto-fix.

PHASE 11:
Add memory, settings, confirmation system, testing and polish.

IMPORTANT:

Start with PHASE 1 only.

Inspect the repository first.

Then create the Android project skeleton and make sure it builds successfully.

Do not jump to PHASE 2 until PHASE 1 builds.

After PHASE 1 is complete, report:

1. Files created
2. Android SDK/Gradle configuration
3. Build result
4. Any remaining issue
5. Exact next step

Do not merely give me code in chat.

Actually create/modify the files in the current repository.

If the existing repository is not an Android project, safely convert/create the required Android project structure without deleting useful files.

The final goal is a legitimate Android AI Agent inspired by the functionality demonstrated in my reference video, not a UGC/video-generation application.
