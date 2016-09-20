#!/usr/bin/env python
import math

num_strips = 3
num_leds_per_strip = 30

inner_radius = 0.25

spacing = 0.1
lines = []
for strip in range(num_strips):
  theta = float(strip) / num_strips * math.pi * 2

  pixel_count = 0
  for c in range(num_leds_per_strip):
    radius = inner_radius + c*spacing
    x, y, z = radius * math.cos(theta), radius * math.sin(theta), c/10.
    lines.append('  {"point": [%.4f, %.4f, %.4f]}' % (x, y, z))
    pixel_count = pixel_count + 1

  if strip < num_strips-1:
    for d in range(pixel_count, 64):
      lines.append('  {"point": [%.2f, %.2f, %.2f]}' % (999, 999, 999))

print '[\n' + ',\n'.join(lines) + '\n]'
