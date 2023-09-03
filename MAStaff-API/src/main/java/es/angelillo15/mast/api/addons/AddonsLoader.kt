package es.angelillo15.mast.api.addons

import com.google.inject.Inject
import com.google.inject.Singleton
import es.angelillo15.mast.api.addons.annotations.Addon
import es.angelillo15.mast.api.addons.annotations.Addon.AddonPlatform
import es.angelillo15.mast.api.utils.MAStaffInject
import org.reflections.Reflections

@Singleton
class AddonsLoader {
    @Inject
    private lateinit var instance: MAStaffInject;
    fun getAddons(platform: AddonPlatform) : MutableSet<Class<*>>? {
        val reflections = Reflections("es.angelillo15")
        val addons = reflections.getTypesAnnotatedWith(Addon::class.java)

        addons.filter {
            MAStaffAddon::class.java.isAssignableFrom(it)
        }.filter {
            it.getAnnotation(Addon::class.java).platform == platform
        }

        return addons
    }

    fun loadAddons(platform: AddonPlatform, instance: MAStaffInject) {
        val addons = getAddons(platform)
        addons?.forEach {

            val addon = instance.injector.getInstance(it) as MAStaffAddon<*>

            addon.onEnable()
        }


    }
}