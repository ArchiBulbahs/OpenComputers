package li.cil.oc.client.renderer.item

import li.cil.oc.Constants
import li.cil.oc.api
import li.cil.oc.api.event.RobotRenderEvent.MountPoint
import li.cil.oc.client.Textures
import li.cil.oc.integration.opencomputers.Item
import li.cil.oc.util.RenderState
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.util.AxisAlignedBB
import org.lwjgl.opengl.GL11

object UpgradeRenderer {
  lazy val craftingUpgrade = api.Items.get(Constants.ItemName.CraftingUpgrade)
  lazy val generatorUpgrade = api.Items.get(Constants.ItemName.GeneratorUpgrade)
  lazy val inventoryUpgrade = api.Items.get(Constants.ItemName.InventoryUpgrade)

  def render(stack: ItemStack, mountPoint: MountPoint): Unit = {
    val descriptor = api.Items.get(stack)

    if (descriptor == api.Items.get(Constants.ItemName.CraftingUpgrade)) {
      Textures.bind(Textures.Model.UpgradeCrafting)
      drawSimpleBlock(mountPoint)

      RenderState.checkError(getClass.getName + ".renderItem: crafting upgrade")
    }

    else if (descriptor == api.Items.get(Constants.ItemName.GeneratorUpgrade)) {
      Textures.bind(Textures.Model.UpgradeGenerator)
      drawSimpleBlock(mountPoint, if (Item.dataTag(stack).getInteger("remainingTicks") > 0) 0.5f else 0)

      RenderState.checkError(getClass.getName + ".renderItem: generator upgrade")
    }

    else if (descriptor == api.Items.get(Constants.ItemName.InventoryUpgrade)) {
      Textures.bind(Textures.Model.UpgradeInventory)
      drawSimpleBlock(mountPoint)

      RenderState.checkError(getClass.getName + ".renderItem: inventory upgrade")
    }
  }

  private val bounds = AxisAlignedBB.fromBounds(-0.1, -0.1, -0.1, 0.1, 0.1, 0.1)

  private def drawSimpleBlock(mountPoint: MountPoint, frontOffset: Float = 0) {
    GL11.glRotatef(mountPoint.rotation.getW, mountPoint.rotation.getX, mountPoint.rotation.getY, mountPoint.rotation.getZ)
    GL11.glTranslatef(mountPoint.offset.getX, mountPoint.offset.getY, mountPoint.offset.getZ)

    GL11.glBegin(GL11.GL_QUADS)

    // Front.
    GL11.glNormal3f(0, 0, 1)
    GL11.glTexCoord2f(frontOffset, 0.5f)
    GL11.glVertex3d(bounds.minX, bounds.minY, bounds.maxZ)
    GL11.glTexCoord2f(frontOffset + 0.5f, 0.5f)
    GL11.glVertex3d(bounds.maxX, bounds.minY, bounds.maxZ)
    GL11.glTexCoord2f(frontOffset + 0.5f, 0)
    GL11.glVertex3d(bounds.maxX, bounds.maxY, bounds.maxZ)
    GL11.glTexCoord2f(frontOffset, 0)
    GL11.glVertex3d(bounds.minX, bounds.maxY, bounds.maxZ)

    // Top.
    GL11.glNormal3f(0, 1, 0)
    GL11.glTexCoord2f(1, 0.5f)
    GL11.glVertex3d(bounds.maxX, bounds.maxY, bounds.maxZ)
    GL11.glTexCoord2f(1, 1)
    GL11.glVertex3d(bounds.maxX, bounds.maxY, bounds.minZ)
    GL11.glTexCoord2f(0.5f, 1)
    GL11.glVertex3d(bounds.minX, bounds.maxY, bounds.minZ)
    GL11.glTexCoord2f(0.5f, 0.5f)
    GL11.glVertex3d(bounds.minX, bounds.maxY, bounds.maxZ)

    // Bottom.
    GL11.glNormal3f(0, -1, 0)
    GL11.glTexCoord2f(0.5f, 0.5f)
    GL11.glVertex3d(bounds.minX, bounds.minY, bounds.maxZ)
    GL11.glTexCoord2f(0.5f, 1)
    GL11.glVertex3d(bounds.minX, bounds.minY, bounds.minZ)
    GL11.glTexCoord2f(1, 1)
    GL11.glVertex3d(bounds.maxX, bounds.minY, bounds.minZ)
    GL11.glTexCoord2f(1, 0.5f)
    GL11.glVertex3d(bounds.maxX, bounds.minY, bounds.maxZ)

    // Left.
    GL11.glNormal3f(1, 0, 0)
    GL11.glTexCoord2f(0, 0.5f)
    GL11.glVertex3d(bounds.maxX, bounds.maxY, bounds.maxZ)
    GL11.glTexCoord2f(0, 1)
    GL11.glVertex3d(bounds.maxX, bounds.minY, bounds.maxZ)
    GL11.glTexCoord2f(0.5f, 1)
    GL11.glVertex3d(bounds.maxX, bounds.minY, bounds.minZ)
    GL11.glTexCoord2f(0.5f, 0.5f)
    GL11.glVertex3d(bounds.maxX, bounds.maxY, bounds.minZ)

    // Right.
    GL11.glNormal3f(-1, 0, 0)
    GL11.glTexCoord2f(0, 1)
    GL11.glVertex3d(bounds.minX, bounds.minY, bounds.maxZ)
    GL11.glTexCoord2f(0, 0.5f)
    GL11.glVertex3d(bounds.minX, bounds.maxY, bounds.maxZ)
    GL11.glTexCoord2f(0.5f, 0.5f)
    GL11.glVertex3d(bounds.minX, bounds.maxY, bounds.minZ)
    GL11.glTexCoord2f(0.5f, 1)
    GL11.glVertex3d(bounds.minX, bounds.minY, bounds.minZ)

    GL11.glEnd()
  }
}