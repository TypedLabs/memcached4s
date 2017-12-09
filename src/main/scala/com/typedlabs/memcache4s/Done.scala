package com.typedlabs.memcached4s

/**
 * Typically used together with `Future` to signal completion
 * but there is no actual value completed. More clearly signals intent than `Unit`.
 */
sealed abstract class Done extends Serializable

case object Done extends Done