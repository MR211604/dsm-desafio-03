---
name: Systems Engineering Resource Management
colors:
  surface: '#131313'
  surface-dim: '#131313'
  surface-bright: '#393939'
  surface-container-lowest: '#0e0e0e'
  surface-container-low: '#1c1b1b'
  surface-container: '#201f1f'
  surface-container-high: '#2a2a2a'
  surface-container-highest: '#353534'
  on-surface: '#e5e2e1'
  on-surface-variant: '#cdc3d5'
  inverse-surface: '#e5e2e1'
  inverse-on-surface: '#313030'
  outline: '#968d9f'
  outline-variant: '#4b4453'
  surface-tint: '#d7baff'
  primary: '#d7baff'
  on-primary: '#430088'
  primary-container: '#8d53e0'
  on-primary-container: '#fff9fd'
  inverse-primary: '#773bc9'
  secondary: '#d1ffa0'
  on-secondary: '#1c3700'
  secondary-container: '#8bed00'
  on-secondary-container: '#3a6700'
  tertiary: '#d6bcf4'
  on-tertiary: '#3b2754'
  tertiary-container: '#816a9c'
  on-tertiary-container: '#fffafc'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#eddcff'
  primary-fixed-dim: '#d7baff'
  on-primary-fixed: '#280056'
  on-primary-fixed-variant: '#5e1ab0'
  secondary-fixed: '#98fc20'
  secondary-fixed-dim: '#82dd00'
  on-secondary-fixed: '#0e2000'
  on-secondary-fixed-variant: '#2c5000'
  tertiary-fixed: '#eedbff'
  tertiary-fixed-dim: '#d6bcf4'
  on-tertiary-fixed: '#25113e'
  on-tertiary-fixed-variant: '#523d6c'
  background: '#131313'
  on-background: '#e5e2e1'
  surface-variant: '#353534'
typography:
  display-lg:
    fontFamily: Inter
    fontSize: 57px
    fontWeight: '700'
    lineHeight: 64px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '600'
    lineHeight: 40px
  headline-md:
    fontFamily: Inter
    fontSize: 28px
    fontWeight: '600'
    lineHeight: 36px
  title-lg:
    fontFamily: Inter
    fontSize: 22px
    fontWeight: '500'
    lineHeight: 28px
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  label-md:
    fontFamily: JetBrains Mono
    fontSize: 12px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.5px
rounded:
  sm: 0.125rem
  DEFAULT: 0.25rem
  md: 0.375rem
  lg: 0.5rem
  xl: 0.75rem
  full: 9999px
spacing:
  base: 8px
  xs: 4px
  sm: 12px
  md: 24px
  lg: 40px
  xl: 64px
  gutter: 24px
  margin: 32px
---

## Brand & Style

The visual identity of this design system is rooted in "Technical Energy"—a fusion of rigorous engineering logic and the vibrant momentum of modern learning. It adapts Material 3 principles into a high-performance dark-mode environment tailored for Systems Engineering students and educators.

The style is **Modern Minimalist with Tonal Depth**. It avoids unnecessary skeuomorphism, favoring clean lines, purposeful whitespace, and a high-contrast accent palette that guides the eye toward critical information. By combining deep, muted surface tones with a neon secondary accent, the system creates a focused, "head-up display" (HUD) feel that feels both professional and cutting-edge.

## Colors

The palette is optimized for long-duration study sessions in low-light environments. 

*   **Primary (#8D53E0):** A deep, electric purple used for brand presence, primary actions, and progress indicators.
*   **Secondary (#8CEE00):** A "Cyber Lime" used sparingly as a high-visibility signal. It marks active states, success notifications, and interactive highlights.
*   **Surface Strategy:** Instead of pure blacks, the system uses "Deep Space Grays" with a subtle purple tint (#1C1C22) for containers. This maintains depth and prevents the "crushed black" effect on OLED screens.
*   **Accents:** Use the secondary color for technical callouts (e.g., "Live," "System Online," or "Verified") to inject energy into the professional layout.

## Typography

The typography system prioritizes legibility for complex data. **Inter** provides a clean, neutral canvas for headlines and body text, while **JetBrains Mono** is introduced for labels and technical metadata to reinforce the engineering aesthetic.

*   **Headlines:** Large and bold to establish immediate hierarchy in resource-heavy views.
*   **Body:** Optimized with generous line heights (1.5x) to ensure academic papers and technical descriptions are readable.
*   **Technical Labels:** Use monospaced font styles for version numbers, system IDs, and timestamps to provide a distinct visual "texture" compared to prose.

## Layout & Spacing

This design system utilizes a **12-column fluid grid** for desktop and a **4-column grid** for mobile. The rhythm is based on an 8px baseline to ensure consistency across technical diagrams and UI components.

*   **Margins & Gutters:** Large 32px margins provide the "Minimalist" breathing room required to balance high-density engineering data.
*   **Logical Grouping:** Use `md` (24px) spacing between related cards and `lg` (40px) spacing between distinct content sections.
*   **Density:** For data-heavy tables or resource lists, a "Compact" mode is supported, reducing vertical padding by 50% while maintaining the 8px alignment.

## Elevation & Depth

Depth is conveyed through **Tonal Layering** and **Subtle Outlines** rather than heavy drop shadows.

*   **Z-0 (Background):** The darkest layer (#0D0D0F), used for the main application canvas.
*   **Z-1 (Cards/Surfaces):** A slightly lighter gray (#16161A) with a 1px solid border (#2D2D35).
*   **Z-2 (Modals/Overlays):** The lightest surface (#1C1C22) with a primary-tinted glow (Primary color at 10% opacity, 20px blur).
*   **Interactive State:** On hover, cards should transition their border color from the neutral outline to the Primary Purple (#8D53E0), creating a responsive "lit" effect.

## Shapes

The shape language is **Soft (0.25rem / 4px)**. This choice reflects engineering precision—avoiding the overly "bubbly" feel of consumer apps while remaining more approachable than hard-edged brutalism.

*   **Standard Components:** Buttons, input fields, and small chips use the 4px radius.
*   **Containers:** Large cards and resource blocks use the `rounded-lg` (8px) radius to softly frame content.
*   **Specialty:** Status pips and "Live" indicators remain circular to contrast against the predominantly rectangular grid.

## Components

### Buttons & Interaction
*   **Primary Action:** Solid Primary Purple with white text. High-contrast, bold.
*   **Secondary Action:** Outlined with a 1px border. On hover, the background fills with a 10% opacity Lime Green tint.
*   **Ghost Action:** Monospaced labels for utility actions (e.g., "EXPORT_DATA").

### Cards
Cards are the primary vehicle for learning resources. They feature:
*   A 1px subtle border (#2D2D35).
*   A top-accent bar (2px height) using the Secondary Lime Green for "New" or "Required" items.
*   Internal padding of 24px to ensure content does not feel cramped.

### Chips & Tags
Used for categorizing engineering disciplines (e.g., "Thermodynamics," "Logic Gates"). Tags use a deep purple background (#2E1A47) with light purple text to remain legible without competing with primary buttons.

### Form Inputs
Minimalist fields with only a bottom border in the resting state. Upon focus, the border animates to a full 1px box in Primary Purple with a subtle Lime Green cursor or focus ring for high-energy feedback.

### Engineering-Specific Components
*   **System Status Bar:** A persistent thin header utilizing monospaced type and Lime Green pips to indicate sync status or system health.
*   **Resource Progress Rail:** A thin, 4px height bar at the bottom of resource cards showing completion percentage in Primary Purple.