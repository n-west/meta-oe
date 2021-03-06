DESCRIPTION = "Configuration file for kexecboot"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"

PR = "r14"

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "file://icon.xpm"

CMDLINE ?= ""
CMDLINE_DEBUG ?= "quiet"

# Note: for qvga the label is currently limited to about 24 chars
KEXECBOOT_LABEL ?= "${@d.getVar('DISTRO', True) or d.getVar('DISTRO_VERSION', True)}-${MACHINE}"

do_configure_prepend () {
    install -m 0644 ${WORKDIR}/icon.xpm ${S}
}

do_install_prepend () {
echo '# First kernel stanza.
# Specify full kernel path on target.
KERNEL=/boot/${KERNEL_IMAGETYPE}

# Show this label in kexecboot menu.
LABEL=${KEXECBOOT_LABEL}
#
# Append this tags to the kernel cmdline.
APPEND=${CMDLINE} ${CMDLINE_DEBUG}
#
# Specify optional initrd/initramfs.
# INITRD=/boot/initramfs.cpio.gz
#
# Specify full path for a custom icon for the menu-item.
# If not set, use device-icons as default (NAND, SD, CF, ...).
# ICON=/boot/icon.xpm
#
# Priority of item in kexecboot menu.
# Items with highest priority will be shown at top of menu.
# Default: 0 (lowest, ordered by device ordering)
# PRIORITY=10
#
#
# Second kernel stanza.
# KERNEL=/boot/${KERNEL_IMAGETYPE}-test
# LABEL=${KEXECBOOT_LABEL}-test
# APPEND=${CMDLINE}
#' >> ${S}/boot.cfg
}

do_install () {
        install -d ${D}/boot
        install -m 0644 boot.cfg ${D}/boot/boot.cfg
        install -m 0644 icon.xpm ${D}/boot/icon.xpm
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

FILES_${PN} += "/boot/*"
